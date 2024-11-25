package tasks.extremumSearch.rosenbrock

import kgal.chromosome.Chromosome
import kgal.chromosome.base.doubles
import kgal.distributed.*
import kgal.distributed.operators.migration
import kgal.name
import kgal.operators.stopBy
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxSimulatedBinaryBounded
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutPolynomialBounded
import kgal.panmictic.operators.selection.selTournament
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kotlin.math.pow
import kotlin.random.Random

// Constants (can be changed)
private const val DISTRIBUTED_GA_COUNT = 4
private const val DISTRIBUTED_MAX_ITERATION = 5
private const val POPULATION_SIZE = 50
private const val CHROMOSOME_SIZE = 2
private const val RANDOM_SEED = 42
private const val ELITISM = 5
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.9
private const val MUTATION_CHANCE = 0.5
private const val POLYNOMIAL_BOUNDED_CHANCE = 0.5
private const val MAX_ITERATION = 20
private const val LOW = -100.0
private const val UP = 100.0
private const val ETA = 20.0

/**
 * Solution to find the minimum of the `Rosenbrock function` by [DistributedGA].
 *
 * `Rosenbrock function` is a non-convex function, introduced by Howard H. Rosenbrock in 1960,
 * which is used as a performance test problem for optimization algorithms.
 * Rosenbrock function has 1 global minimum:
 * 1) `f(1.0, 1.0) = 0`
 *
 * [Chromosome.value]: [DoubleArray] with `size = 2 (x, y)` = `[1.0, 2.0]`
 *
 * [Chromosome.fitness]: [Double] = negative value of `Rosenbrock function` by chromosome value (minimum finding)
 *
 * `TARGET`: `-0.0001 < fitness < 0.0001` (delta for floating point numbers)
 *
 * The Initial Population contains `randomly` created DoubleArrays:
 * ```
 * [
 * [100.0, 20.0],
 * [14.1, 48.289],
 * [12.139, -35.12],
 * ...
 * [-13.52, 34.1205],
 * ]
 * ```
 *
 * Solution:
 *
 * Get the `TARGET` using `standard evolutionary strategy`: `selection`→`crossover`→`mutation`→`evaluation`:
 * @see <a href="https://en.wikipedia.org/wiki/Rosenbrock_function">Rosenbrock function</a>
 * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Rosenbrock-contour.svg/1014px-Rosenbrock-contour.svg.png"/>
 */
private fun main() { // Run it!

    /**
     * Rosenbrock function
     */
    fun rosenbrock(x: Double, y: Double): Double =
        (1 - x).pow(2) + 100 * (y - x.pow(2.0)).pow(2)

    /**
     * Stop condition (minimum is find with accuracy in delta)
     */
    fun stopCondition(chromosome: Chromosome<DoubleArray, Double>): Boolean =
        chromosome.fitness!! in -0.001..0.001

    val dga = dGA(
        factory = { doubles(size = CHROMOSOME_SIZE, from = LOW, until = UP) }, // common factory for subpopulations
        fitnessFunction = { value -> -rosenbrock(value[0], value[1]) }, // common fitness function for evaluation stage
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
                stopBy(maxIteration = MAX_ITERATION) { population.any(::stopCondition) } // stop condition
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