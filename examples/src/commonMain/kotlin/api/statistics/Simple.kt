package api.statistics

import kgal.chromosome.base.booleans
import kgal.collect
import kgal.distributed.DistributedGA
import kgal.operators.stopBy
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.panmictic.pGA
import kgal.panmictic.population
import kgal.startBlocking
import kgal.statistics.note.Statistic
import kgal.statistics.note.stat
import kgal.statistics.stats.*
import kgal.statisticsConfig
import kotlin.random.Random

// Constants (can be changed)
private const val POPULATION_SIZE = 200
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42
private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01
private const val MAX_ITERATION = 50

/**
 * Simple example for using statistics.
 *
 * See [DistributedGA] `shared statistics` in advanced example.
 */
private fun main() { // Run it!
    // STEP 1: create Panmictic GA for resolving One Max Problem
    val pga = pGA(
        population = population(size = POPULATION_SIZE) { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } },
    ) {
        random = Random(seed = RANDOM_SEED)
        elitism = ELITISM

        // STEP 2 (optional): configure statistics
        statisticsConfig {
            enableDefaultCollector = false // set to true (default) and remove collect below (STEP 4), see result
        }

        evolve {
            selTournament(size = TOURNAMENT_SIZE)
            cxOnePoint(chance = CROSSOVER_CHANCE)
            mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE)
            evaluation()
            // STEP 3: add statistics stage in evolution function
            stat(
                timeIteration(), // evaluates time per iteration
                bestFitness(), // evaluates the best fitness
                mean(), // evaluates mean fitness value of population
                median(), // evaluates median fitness value of population
                worstFitness(), // evaluates the worst fitness
                // send also custom statistics
                Statistic(name = "CUSTOM: TIME STORE onIteration size", value = timeStore.onIteration.size),
            )
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE }
        }
    }

    // STEP 4: collect statistics with custom collector
    pga.collect(id = "My custom statistics collector") { statNote ->
        println("My custom statistics collector collects:")
        println(statNote)
    }
    pga.startBlocking()
}
