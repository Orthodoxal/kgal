package api.controller

import kgal.GA
import kgal.StopPolicy
import kgal.chromosome.base.booleans
import kgal.operators.stopBy
import kgal.panmictic.*
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.statistics.allSessions
import kgal.statistics.note.stat
import kgal.statistics.stats.activeTimeTotal
import kgal.statistics.stats.bestFitness
import kgal.statistics.stats.timeIteration
import kgal.statistics.stats.timeTotal
import kotlinx.coroutines.*
import kotlin.random.Random

// Constants (can be changed)
private const val MAX_ITERATION = 50
private const val POPULATION_SIZE = 200
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42
private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01

/**
 * Example of interactive api ([GA.start], [GA.resume], [GA.stop], [GA.restart]).
 *
 * Resolving One Max Problem with [PanmicticGA] (Classical GA).
 */
private fun main() { // Run it!
    // STEP 1: create simple PanmicticGA for One Max Problem
    val pga = pGA(
        population = population(size = POPULATION_SIZE) { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } },
    ) {
        random = Random(seed = RANDOM_SEED)
        elitism = ELITISM

        // STEP 2: set up action on start evolution
        before {
            println("GA STARTED, Init population fitness values:\n${population.joinToString { it.fitness.toString() }}")
        }

        evolve {
            // STEP 3: imitate hardworking
            delay(timeMillis = 200)

            selTournament(size = TOURNAMENT_SIZE)
            cxOnePoint(chance = CROSSOVER_CHANCE)
            mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE)
            evaluation()
            // STEP 4: add some statistics
            stat(timeIteration(), bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE }
        }

        // STEP 5: set up action on finish evolution
        after {
            println("GA FINISHED, Result = $bestFitness on iteration: $iteration")
        }
    }

    // STEP 6: get coroutine scope from runBlocking
    runBlocking {
        // Start GA
        println("Started")
        launch(Dispatchers.Default) { pga.start() }

        // Wait a little bit
        delay(timeMillis = 1_000)

        // Stop GA
        println("Stopped by default")
        pga.stop(stopPolicy = StopPolicy.Default)

        // Waiting
        delay(timeMillis = 2_000)

        // Resume GA
        println("Resumed")
        launch(Dispatchers.Default) { pga.resume() }

        // Waiting
        delay(timeMillis = 5_000)

        // Restart GA
        println("Restarted")
        launch(Dispatchers.Default) { pga.restart() }

        // Waiting
        delay(timeMillis = 3_000)

        // Stop GA with Timeout policy
        println("Stopped by timeout")
        pga.stop(stopPolicy = StopPolicy.Timeout(millis = 100))

        // Restart GA again (On Main Thread)
        println("Restarted on main thread")
        println(coroutineContext.job)
        pga.restart()
        println("Best fitness result for population ${pga.population.name}: ${pga.bestFitness}")
    }

    // STEP 7: Explore GA sessions
    pga.allSessions.print()
    // Total duration: STARTED -> LAST TIME VALUE
    println("Time total: ${pga.timeTotal} // includes collectors waiting")
    // Total activity duration:
    // STARTED -> FINISHED + STARTED (by restart) -> STOPPED + STARTED -> FINISHED
    println("Activity time total: ${pga.activeTimeTotal} // without stops, includes collectors waiting")
}

