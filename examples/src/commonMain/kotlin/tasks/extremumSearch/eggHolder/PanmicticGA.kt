package tasks.extremumSearch.eggHolder

import kgal.chromosome.Chromosome
import kgal.chromosome.base.doubles
import kgal.operators.stopBy
import kgal.panmictic.PanmicticGA
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxSimulatedBinaryBounded
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutPolynomialBounded
import kgal.panmictic.operators.selection.selTournament
import kgal.panmictic.pGA
import kgal.panmictic.population
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kotlin.math.abs
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

// Constants (can be changed)
private const val POPULATION_SIZE = 200
private const val CHROMOSOME_SIZE = 2
private const val RANDOM_SEED = 42
private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.9
private const val MUTATION_CHANCE = 0.5
private const val POLYNOMIAL_BOUNDED_CHANCE = 0.5
private const val MAX_ITERATION = 50
private const val LOW = -512.0
private const val UP = 512.0
private const val ETA = 10.0

/**
 * Solution to find the minimum of the `EggHolder function` by [PanmicticGA] (Classical GA).
 *
 * The `EggHolder function` is a difficult function to optimize, because of the large number of local minima.
 * The function is usually evaluated on the square `[-512.0, 512.0]`.
 * `EggHolder function` has only 1 global minimum:
 * 1) `f(512.0, 404.2319) = -959.6407`
 *
 * [Chromosome.value]: [DoubleArray] with `size = 2 (x, y)` = `[1.0, 2.0]`
 *
 * [Chromosome.fitness]: [Double] = negative value of `EggHolder function` by chromosome value (minimum finding)
 *
 * `TARGET`: `959.4 < fitness < 959.9` (delta for floating point numbers)
 *
 * The Initial Population contains `randomly` created DoubleArrays:
 * ```
 * [
 * [100.0, 20.0],
 * [104.1, 48.289],
 * [12.139, -335.12],
 * ...
 * [-123.52, 364.1205],
 * ]
 * ```
 *
 * Solution:
 *
 * Get the `TARGET` using `standard evolutionary strategy`: `selection`→`crossover`→`mutation`→`evaluation`:
 * @see <a href=https://www.sfu.ca/~ssurjano/egg.html">EggHolder function</a>
 * @see <img src="https://www.sfu.ca/~ssurjano/egg.png"/>
 */
private fun main() { // Run it!

    /**
     * EggHolder function
     */
    fun eggHolder(x: Double, y: Double): Double =
        -(y + 47) * sin(sqrt(abs(y + x / 2 + 47))) - x * sin(sqrt(abs(x - (y + 47))))

    /**
     * Stop condition (minimum is find with accuracy in delta)
     */
    fun stopCondition(chromosome: Chromosome<DoubleArray, Double>): Boolean =
        chromosome.fitness!! in 959.4..959.9

    val pga = pGA(
        // create population of booleans chromosomes
        population = population(size = POPULATION_SIZE) { doubles(size = CHROMOSOME_SIZE, from = LOW, until = UP) },
        fitnessFunction = { value -> -eggHolder(value[0], value[1]) }, // fitness function for evaluation stage
    ) {
        random = Random(seed = RANDOM_SEED) // set random generator
        elitism = ELITISM // enable elitism

        // create panmictic evolution strategy
        evolve {
            selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)

            cxSimulatedBinaryBounded(
                // crossover ('cx' prefix)
                chance = CROSSOVER_CHANCE,
                eta = ETA,
                low = LOW,
                up = UP,
            )

            mutPolynomialBounded(
                // mutation ('mut' prefix)
                eta = ETA,
                low = LOW,
                up = UP,
                chance = MUTATION_CHANCE,
                polynomialBoundedChance = POLYNOMIAL_BOUNDED_CHANCE,
            )
            evaluation() // evaluate all offspring
            println("Iteration $iteration: best fitness = $bestFitness") // Or use stat(bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { population.any(::stopCondition) } // stop condition
        }
    }
    pga.startBlocking() // start on Main Thread

    println("Result fitness: ${pga.bestFitness}")
    println("Result chromosome: ${pga.best?.value?.joinToString()}")
}
