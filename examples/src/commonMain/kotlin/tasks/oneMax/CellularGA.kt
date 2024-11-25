package tasks.oneMax

import kgal.cellular.*
import kgal.cellular.neighborhood.Moore
import kgal.cellular.operators.crossover.cxOnePoint
import kgal.cellular.operators.evaluation
import kgal.cellular.operators.evolveCells
import kgal.cellular.operators.mutation.mutFlipBit
import kgal.cellular.operators.selection.selTournament
import kgal.chromosome.Chromosome
import kgal.chromosome.base.booleans
import kgal.operators.stopBy
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kotlin.random.Random

// Constants (can be changed)
private const val DIMEN_SIZE = 6
private val DIMENS = Dimens.cube(length = DIMEN_SIZE)
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42
private const val ELITISM = true
private val CELLULAR_TYPE = CellularType.Synchronous
private val CELLULAR_NEIGHBORHOOD = Moore(radius = 1)
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01
private const val MAX_ITERATION = 50

/**
 * Resolving One Max Problem by [CellularGA].
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
    val cga = cGA(
        // create population of booleans chromosomes (Population size = 6 * 6 * 6 = 216 (cube))
        population = population(dimens = DIMENS) { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } }, // fitness function for evaluation stage
    ) {
        random = Random(seed = RANDOM_SEED) // set random generator
        elitism = ELITISM // enable elitism
        cellularType = CELLULAR_TYPE // set type of Cellular GA
        neighborhood = CELLULAR_NEIGHBORHOOD // set neighborhood for Cellular Population

        // create cellular evolution strategy
        evolve {
            // Start to evolve all cells of cellular population with their neighborhoods
            // This operator perform N evolutionary strategies on each cell, where N - cells count
            evolveCells {
                selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
                cxOnePoint(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
                mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE) // mutation ('mut' prefix)
                evaluation() // evaluate child
            }

            println("Iteration $iteration: best fitness = $bestFitness") // Or use stat(bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE } // stop condition
        }
    }
    cga.startBlocking() // start on Main Thread

    println("Result fitness: ${cga.bestFitness}")
    println("Result chromosome: ${cga.best?.value?.joinToString()}")
}
