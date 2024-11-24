package tasks.oneMax

import kgal.chromosome.Chromosome
import kgal.chromosome.base.booleans
import kgal.operators.stopBy
import kgal.panmictic.PanmicticGA
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.panmictic.pGA
import kgal.panmictic.population
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
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
 * Resolving One Max Problem by [PanmicticGA] (Classical GA).
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
    val pga = pGA(
        // create population of booleans chromosomes
        population = population(size = POPULATION_SIZE) { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } }, // fitness function for evaluation stage
    ) {
        random = Random(seed = RANDOM_SEED) // set random generator
        elitism = ELITISM // enable elitism

        // create panmictic evolution strategy
        evolve {
            selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
            cxOnePoint(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
            mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE) // mutation ('mut' prefix)
            evaluation() // evaluate all offspring
            println("Iteration $iteration: best fitness = $bestFitness") // Or use stat(bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE } // stop condition
        }
    }
    pga.startBlocking() // start on Main Thread

    println("Result fitness: ${pga.bestFitness}")
    println("Result chromosome: ${pga.best?.value?.joinToString()}")
}
