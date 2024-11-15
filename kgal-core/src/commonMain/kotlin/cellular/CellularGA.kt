package kgal.cellular

import kgal.AbstractGA
import kgal.GA
import kgal.cellular.operators.isCacheNeighborhoodActual
import kgal.cellular.operators.replaceWithElitism
import kgal.chromosome.Chromosome

/**
 * [CellularGA] - cellular genetic algorithm (cGA).
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * A cellular genetic algorithm is a kind of genetic algorithm in which individuals cannot mate arbitrarily,
 * but every one interacts with its closer neighbors on which a `basic evolution strategy`
 * is applied (`selection`, `crossover`, `mutation`, `evaluation`).
 *
 * Creates with Kotlin DSL by [cGA].
 * @see <a href="https://en.wikipedia.org/wiki/Cellular_evolutionary_algorithm">Cellular Genetic Algorithm</a>
 * @see <a href="https://link.springer.com/book/10.1007/978-0-387-77610-1">Cellular Genetic Algorithm by Enrique Alba</a>
 * @see CellularPopulation
 * @see CellularLifecycle
 * @see CellLifecycle
 * @see CellularConfig
 */
public interface CellularGA<V, F> : GA<V, F> {

    /**
     * Population of homogeneous chromosomes for [CellularGA].
     */
    override val population: CellularPopulation<V, F>

    /**
     * Flag for elitism in [CellularGA].
     *
     * If `true` parent chromosome will be replaced with child only if `child.fitness > parent.fitness`.
     * @see replaceWithElitism
     */
    public var elitism: Boolean

    /**
     * Cellular type for [CellularGA].
     * @see CellularType
     */
    public var cellularType: CellularType

    /**
     * Neighborhood for [CellularGA].
     * @see [CellularNeighborhood]
     */
    public var neighborhood: CellularNeighborhood

    public companion object {

        /**
         * Creates [CellularGA] with [configuration].
         */
        public fun <V, F> create(
            configuration: CellularConfig<V, F>,
        ): CellularGA<V, F> = CellularGAInstance(configuration)
    }
}

/**
 * Creates [CellularGA] with Kotlin DSL.
 *
 * Example for OneMax task:
 * ```
 * cGA(
 *     // Set a population configuration
 *     // population size = 6 * 6 * 6 = 216 (cube)
 *     population = population(dimens = Dimens.cube(length = 6)) {
 *         booleans(size = 100)
 *     },
 *     // Set a fitness function
 *     fitnessFunction = { value -> value.count { it } },
 * {
 *     random = Random(seed = 42) // set pseudo random number generator
 *     elitism = true // set elitism
 *     cellularType = CellularType.Synchronous // set cellular type
 *     neighborhood = Moore(radius = 1) // set cellular neighborhood
 *
 *     before {
 *       println("GA STARTED, Init population: $population")
 *     }
 *
 *     evolve {
 *         // Start to evolve all cells of cellular population with their neighborhoods
 *         // This operator perform N evolutionary strategies for each cell, where N - cells count
 *         evolveCells {
 *             selTournament(size = 3) // select neighbor as second parent
 *             cxOnePoint(chance = 0.8) // crossover
 *             mutFlipBit(chance = 0.2, flipBitChance = 0.01) // mutate child
 *             evaluation() // evaluate child
 *         }
 *
 *         stopBy(maxIteration = 50) { bestFitness == 100 } // finish GA by conditions
 *     }
 *
 *     after {
 *         println("GA FINISHED, Result = $best")
 *     }
 * }.startBlocking()
 * ```
 * @param population population of [CellularGA]
 * @param fitnessFunction fitness function for evaluation step
 * @param config scope function to initialize [CellularGA]
 *
 * @see <a href="https://en.wikipedia.org/wiki/Cellular_evolutionary_algorithm">Cellular Genetic Algorithm</a>
 * @see <a href="https://link.springer.com/book/10.1007/978-0-387-77610-1">Cellular Genetic Algorithm by Enrique Alba</a>
 * @see CellularGA
 * @see CellularPopulation
 * @see CellularLifecycle
 * @see CellLifecycle
 * @see CellularConfig
 */
public inline fun <V, F> cGA(
    population: CellularPopulation<V, F>,
    noinline fitnessFunction: (V) -> F,
    config: CellularConfigScope<V, F>.() -> Unit,
): CellularGA<V, F> =
    CellularGA.create(
        configuration = CellularConfigScope(population, fitnessFunction).apply(config)
    )

/**
 * Base realization of [CellularGA].
 * @param configuration for initialization [CellularGA]
 * @see AbstractGA
 */
internal class CellularGAInstance<V, F>(
    configuration: CellularConfig<V, F>,
) : CellularGA<V, F>, AbstractGA<V, F, CellularLifecycle<V, F>>(configuration) {

    override val population: CellularPopulation<V, F> = configuration.population

    override val lifecycle: CellularLifecycle<V, F> by lazy { CellularLifecycle(this, configuration) }

    override var elitism: Boolean = configuration.elitism

    override var cellularType: CellularType = configuration.cellularType

    override var neighborhood: CellularNeighborhood = configuration.neighborhood
        set(value) {
            if (field != value) lifecycle.isCacheNeighborhoodActual = false
            field = value
        }
}
