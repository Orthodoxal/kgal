package panmictic

import kgal.chromosome.base.booleans
import kgal.operators.stopBy
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.panmictic.pGA
import kgal.panmictic.population
import kgal.reset
import kgal.startBlocking
import kgal.statistics.note.stat
import kgal.statistics.stats.*
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.measureTime

private const val POPULATION_SIZE = 200
private const val CHROMOSOME_SIZE = 100
private const val SEED = 42
private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01
private const val MAX_ITERATION = 50

fun main() {
    pGA(
        // Set population configuration
        population = population(size = POPULATION_SIZE) { booleans(size = CHROMOSOME_SIZE) },
        // Set fitness function
        fitnessFunction = { value -> value.count { it } },
    ) {
        random = Random(seed = SEED) // set pseudo random number generator
        elitism = ELITISM // set elitism

        before {
            println("GA STARTED, Init population fitness values:\n${population.joinToString { it.fitness.toString() }}")
        }

        // create evolution strategy
        evolve {
            selTournament(size = TOURNAMENT_SIZE) // select
            cxOnePoint(chance = CROSSOVER_CHANCE) // crossover
            mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE) // mutate
            evaluation() // evaluate population
            stat(bestFitness(), mean(), worstFitness())
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE } // finish GA by conditions
        }

        after {
            println("GA FINISHED, Result = $bestFitness on iteration: $iteration")
        }
    }.startBlocking()
}

private fun loadTest() {
    val pga = pGA(
        population = population(size = POPULATION_SIZE) { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } },
    ) {
        random = Random(seed = SEED)
        elitism = ELITISM

        evolve {
            selTournament(size = TOURNAMENT_SIZE)
            cxOnePoint(chance = CROSSOVER_CHANCE)
            mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE)
            evaluation()
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE }
        }
    }

    val times = 10
    var time: Long = 0
    repeat(times) { index ->
        runBlocking {
            time += measureTime { pga.start() }
                .inWholeMilliseconds
        }
        println("For index: $index --- iteration: ${pga.iteration} --- ${pga.bestFitness}")
        pga.population.reset(Random(seed = index))
    }
    println("Average time taken: ${time.toDouble() / times} milliseconds")
}
