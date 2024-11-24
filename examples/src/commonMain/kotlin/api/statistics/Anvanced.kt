package api.statistics

import kgal.GA
import kgal.chromosome.Chromosome
import kgal.chromosome.base.booleans
import kgal.collect
import kgal.distributed.*
import kgal.distributed.operators.migration
import kgal.operators.stopBy
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.startBlocking
import kgal.statistics.note.Statistic
import kgal.statistics.note.stat
import kgal.statistics.note.withName
import kgal.statistics.stats.*
import kgal.statisticsConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

// Constants (can be changed)
private const val DISTRIBUTED_GA_COUNT = 4
private const val DISTRIBUTED_MAX_ITERATION = 5
private const val POPULATION_SIZE = 50
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42
private const val ELITISM = 5
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01
private const val MAX_ITERATION = 20
private const val DISTRIBUTED_NAME = "DISTRIBUTED_POPULATION"

/**
 * Advanced example for using statistics.
 * Demonstrate:
 * - [DistributedGA] `shared statistics`
 * - Advanced [Flow] api for statistics by [GA.statisticsProvider]
 * - Many active collectors
 * - Independence of GA from statistics collectors (error protection)
 */
private fun main() { // Run it!
    // STEP 1: create Distributed GA for resolving One Max Problem
    val dga = dGA(
        factory = { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } },
        populationName = DISTRIBUTED_NAME,
    ) {
        random = Random(seed = RANDOM_SEED)

        parallelismConfig {
            workersCount = DISTRIBUTED_GA_COUNT
        }

        // STEP 2 (optional): make sure enabledSharedStatistic is true
        enabledSharedStatistic = true // must be true (default)

        // STEP 3 (optional): configure statistics
        statisticsConfig {
            defaultCollector = FlowCollector {
                TODO("Oops, no realization for default collector, will it crash GA?")
            }

            /**
             * kgal statistics powered by [SharedFlow], configure flow params here
             * ```
             * replay = 0
             * extraBufferCapacity = 1000
             * onBufferOverflow = BufferOverflow.SUSPEND
             * coroutineContext = Dispatchers.IO
             * ```
             */
        }

        +pGAs(
            count = DISTRIBUTED_GA_COUNT,
            population = { population(size = POPULATION_SIZE) },
        ) {
            elitism = ELITISM

            /**
             * Child that created in DistributedConfig builder (like here) has defaultCollector disabled by default
             *
             * Set statisticsConfig to change this behaviour:
             * ```
             * statisticsConfig {
             *     enableDefaultCollector = true
             * }
             * ```
             */

            evolve {
                selTournament(size = TOURNAMENT_SIZE)
                cxOnePoint(chance = CROSSOVER_CHANCE)
                mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE)
                evaluation()
                // STEP 4: add statistics stage in evolution function of child GA
                stat(
                    bestFitness(), // evaluates the best fitness for island
                )
                stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE }
            }
        }

        evolve {
            // STEP 5: add statistics stage in distributed evolution function
            stat(
                timeIteration(), // evaluates time per distributed iteration
                best(), // evaluates the best chromosome in all subpopulations
                mean(), // evaluates mean fitness value in subpopulations
                median(), // evaluates median fitness value in subpopulations
            )
            stopBy(maxIteration = DISTRIBUTED_MAX_ITERATION)
            migration(percent = 0.1)
        }
    }


    // STEP 6: collect statistics with broken simple collector
    dga.collect {
        // collects shared statistics (children include)
        if (it.iteration == 1) {
            println("Simple collector:$it")
        } else {
            error("Simple collector throw unexpected error")
        }
    }

    // STEP 7: collect statistics with advanced collector 1 and statisticsFlow
    dga.statisticsProvider.collect(id = "Advanced collector 1") { statisticsFlow ->
        statisticsFlow
            .onStart { println("Starting to collect statistic values by advanced collector 1:") }
            .filter { it.ownerName == DISTRIBUTED_NAME } // collect only distributed statistics
            .collect { value ->
                println("Advanced collector 1:\n${value.statistic.name} collected for ${value.iteration} iteration!")
            }
    }

    val bestStore: MutableList<Statistic<Chromosome<BooleanArray, Int?>?>> = mutableListOf()

    // STEP 8: collect statistics with advanced collector 2 and statisticsFlow
    dga.statisticsProvider.collect(id = "Advanced collector 2") { statisticsFlow ->
        statisticsFlow
            .onStart { println("Starting to collect statistic values by advanced collector 2:") }
            .withName("BEST") // collect only BEST stat
            .onEach {
                delay(timeMillis = 500) // Imitate hardworking for collector
            }
            .collect { value ->
                // save statistics to the bestStore
                bestStore.add(value.statistic as Statistic<Chromosome<BooleanArray, Int?>?>)
            }
    }

    dga.startBlocking()
    println("\nNow, all statistics collectors have been finished, blocking ends!")
    println("Results (best chromosomes) for all subpopulations per distributed iterations (size = ${dga.iteration}):")
    bestStore.forEachIndexed { index, stat ->
        println("$index iteration: ${stat.value}")
    }
}
