package tasks.oneMax

import kgal.chromosome.Chromosome
import kgal.chromosome.base.booleans
import kgal.distributed.*
import kgal.distributed.operators.migration
import kgal.name
import kgal.operators.stopBy
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
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

/**
 * Resolving One Max Problem by [DistributedGA] (Island Genetic Algorithm).
 *
 * One Max problem: get the target array of trues `[true, true, true, ... , true]` using Genetic Algorithm
 *
 * [Chromosome.value]: [BooleanArray] = `[true, false, true, ... , false]`
 *
 * [Chromosome.fitness]: [Int] = count of `trues` in chromosome value
 *
 * `TARGET`: `fitness == value.size` (all elements in chromosome value is true)
 *
 * The Initial Population contains `randomly` created BooleanArrays:
 * ```
 * [
 * [true, false, true, ... , true],
 * [true, true, false, ... , true],
 * [false, false, true, ... , false],
 * ...
 * [true, false, true, ... , false],
 * ]
 * ```
 *
 * Solution:
 *
 * Get the `TARGET` using `standard evolutionary strategy`: `selection`→`crossover`→`mutation`→`evaluation`:
 */
private fun main() { // Run it!
    val dga = dGA(
        factory = { booleans(size = CHROMOSOME_SIZE) }, // common factory for subpopulations
        fitnessFunction = { value -> value.count { it } }, // common fitness function for evaluation stage
    ) {
        random = Random(seed = RANDOM_SEED) // set random generator

        // set optimal parallelism configuration
        parallelismConfig {
            // distributed parallelism - run each child GA independently in their own coroutine!
            workersCount = DISTRIBUTED_GA_COUNT
        }

        // add children GAs to distributed configuration
        +pGAs(
            count = DISTRIBUTED_GA_COUNT,
            population = { population(size = POPULATION_SIZE) },
        ) {
            elitism = ELITISM // enable elitism

            // create panmictic evolution strategy
            evolve {
                selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
                cxOnePoint(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
                mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE) // mutation ('mut' prefix)
                evaluation() // evaluate all offspring
                stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE } // stop condition
            }
        }

        // create distributed evolution strategy
        evolve(
            useDefault = true, // (default is true) uses launchChildren (see) operator before evolution function below
        ) {
            println("Iteration $iteration: best fitness = $bestFitness")
            children.forEach { child -> println("${child.name}: best fitness = ${child.bestFitness}") }
            // limit only iterations for dGA not need stop condition by here cause useDefault is true
            stopBy(maxIteration = DISTRIBUTED_MAX_ITERATION)
            migration(percent = 0.1) // set random migration between islands
        }
    }
    dga.startBlocking() // start on Main Thread

    println("Result fitness: ${dga.bestFitness}")
    println("Result chromosome: ${dga.best?.value?.joinToString()}")
}
