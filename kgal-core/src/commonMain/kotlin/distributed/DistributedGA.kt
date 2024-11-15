package kgal.distributed

import kgal.*
import kgal.chromosome.Chromosome
import kgal.distributed.operators.migration
import kgal.processor.parallelism.ParallelismConfig
import kgal.statistics.StatisticsProvider

/**
 * [DistributedGA] - also known as the island genetic algorithm (dGA).
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * [DistributedGA] is an algorithm that divides a large population into subpopulations and
 * runs the genetic algorithm on each subpopulation independently.
 * `Kgal` offers a broader and more universal definition for a [DistributedGA]:
 * dGA is an algorithm that controls the evolutionary processes of child genetic algorithms ([children]).
 *
 * The launched evolutionary processes of child GAs are calculated independently of each other,
 * which allows for efficient and safe use of `parallelism`:
 * set the value [ParallelismConfig.workersCount] equal to the number of child GAs (`children.size`),
 * this will allow [children] to be launched on separate parallel coroutines
 * that achieves almost linear speedup compared to the classical version of the genetic algorithm.
 * ```
 * dGA(...) {
 *     parallelismConfig {
 *         // distributed parallelism:
 *         // run each child GA independently in their own coroutine!
 *         workersCount = DISTRIBUTED_GA_COUNT
 *     }
 * }
 * ```
 *
 * The most popular type for [DistributedGA] is the island GA model, `island evolutionary strategy` includes:
 * 1) launching child genetic algorithms (including parallel mode) `default`
 * 2) synchronization of genetic algorithms (waiting for all to complete) `default`
 * 3) checking stop conditions (`NOTE` don't forget to set a limit on the number of iterations with `stopBy(DISTRIBUTED_MAX_ITERATION)`,
 * otherwise the evolution will happen endlessly)
 * 4) using genetic operators to interact between populations (for example: [migration])
 *
 * The first two steps are automated by `kgal` in the `default` behavior of the function [DistributedConfigScope.evolve],
 * so the `island evolutionary strategy` can be specified as:
 * ```
 * evolve {
 *     // maxIteration stop condition is necessary!
 *     stopBy(maxIteration = DISTRIBUTED_MAX_ITERATION)
 *     migration(percent = 0.1)
 * }
 * ```
 *
 * [DistributedGA] supports `Shared statistics`, see [statisticsProvider].
 *
 * Creates with Kotlin DSL by [dGA].
 * @see DistributedPopulation
 * @see DistributedLifecycle
 * @see DistributedConfig
 */
public interface DistributedGA<V, F> : GA<V, F> {

    /**
     * The [DistributedGA] does not have its own population,
     * but `kgal` defines this property as the total population of all subpopulations of child GAs ([children]).
     *
     * It is possible to safely access this population by an index not exceeding the sum of the sizes of all subpopulations.
     * A common iterator is also available and safe.
     */
    override val population: DistributedPopulation<V, F>

    /**
     * Contains current child genetic algorithms.
     */
    public val children: List<GA<V, F>>

    /**
     * `Shared statistics`: [DistributedGA] is collector for each child statistics -
     * statistics of [DistributedGA] includes all statistics of children:
     * ```
     * val dGA(...) {
     *     +pGAs(...) { index ->
     *         evolve {
     *             ...
     *             stat("Child $index iteration", iteration)
     *         }
     *     }
     *
     *     evolve {
     *         ...
     *         stat("Distributed iteration", iteration)
     *     }
     * }.collect {
     *     // Children and parent iterations collects here
     * }.startBlocking()
     * ```
     */
    override val statisticsProvider: StatisticsProvider

    public companion object {

        /**
         * Creates [DistributedGA] with [configuration].
         */
        public fun <V, F> create(
            configuration: DistributedConfig<V, F>,
        ): DistributedGA<V, F> = DistributedGAInstance(configuration)
    }
}

/**
 * Creates [DistributedGA] with Kotlin DSL.
 *
 * This [GA] supports `Shared statistics` (see [DistributedGA.statisticsProvider])
 *
 * This builder support `Distributed Inheritance` for child created in [config]:
 * - population factory [PopulationFactory]
 * - fitness function
 * - population name [Population.name] with index on which child has been created
 * (see [generateChildName], [DistributedPopulationMultiFactory])
 * - random (each child created in [config] will apply:
 * random = Random(seed = [DistributedGA.random].nextInt) - each child will get unique random)
 *
 * `Distributed Inheritance` - defines the feature for [DistributedGA] to inherit common properties (see above)
 * to their children created in [config] scope. Each parameter from list can be overridden for each child with standard builders.
 *
 * Example for OneMax task (`island evolutionary strategy`):
 * ```
 * dGA(
 *     // default factory for child populations
 *     factory = { booleans(size = 100) },
 *     // default fitness function for child populations
 *     fitnessFunction = { value -> value.count { it } },
 * ) {
 *     // set pseudo random number generator for GA and their children
 *     random = Random(seed = 42)
 *
 *     // set parallelism configuration
 *     parallelismConfig {
 *         // distributed parallelism - run each child GA independently in their own coroutine!
 *         workersCount = 4
 *     }
 *
 *     // create and add children GAs
 *     +pGAs(
 *         count = 4, // island count
 *         population = { population(size = 50) }, // island population
 *     ) {
 *         elitism = 5 // set island elitism
 *
 *         // create evolution strategy for island (can be different for each one)
 *         evolve {
 *             selTournament(size = 3) // island select
 *             cxOnePoint(chance = 0.8) // island crossover
 *             mutFlipBit(chance = 0.2, flipBitChance = 0.01) // island mutation
 *             evaluation() // evaluate island population
 *             stopBy(maxIteration = 20) { bestFitness == 100 } // island stop condition
 *         }
 *     }
 *
 *     // set distributed evolution strategy:
 *     // launches children with ga.start() (default == true) before each iteration
 *     evolve(useDefault = true) {
 *         // limit only iterations for dGA not need stop condition by here cause useDefault is true
 *         stopBy(maxIteration = 5)
 *         // executes migration step between children (islands)
 *         migration(percent = 0.1)
 *     }
 *
 *     after {
 *         println("GA FINISHED, Result = $best")
 *     }
 * }.startBlocking()
 * ```
 * @param factory default population factory for child GAs
 * @param fitnessFunction default fitness function for evaluation step
 * @param populationName name for [DistributedPopulation]
 * @param children child GA of [DistributedGA]
 * (default is empty, recommend to use [pGAs], [cGAs] functions to add children in [config], see example above)
 * @param config scope function to initialize [DistributedGA]
 *
 * @see DistributedGA
 * @see DistributedPopulation
 * @see DistributedLifecycle
 * @see DistributedConfig
 */
public inline fun <V, F> dGA(
    noinline factory: PopulationFactory<V, F>,
    noinline fitnessFunction: (V) -> F,
    populationName: String = DistributedPopulation.DEFAULT_DISTRIBUTED_POPULATION_NAME,
    children: List<GA<V, F>> = emptyList(),
    config: DistributedConfigScope<V, F>.() -> Unit,
): DistributedGA<V, F> =
    DistributedGA.create(
        configuration = DistributedConfigScope(factory, fitnessFunction, populationName, children).apply(config)
    )

/**
 * Base realization of [DistributedGA].
 * @param configuration for initialization [DistributedGA]
 * @see AbstractGA
 */
internal class DistributedGAInstance<V, F>(
    configuration: DistributedConfig<V, F>,
) : DistributedGA<V, F>, AbstractGA<V, F, DistributedLifecycle<V, F>>(configuration) {

    override val population: DistributedPopulation<V, F> = configuration.population

    override val children: List<GA<V, F>> = configuration.children

    override val lifecycle: DistributedLifecycle<V, F> by lazy { DistributedLifecycle(this, configuration) }

    init {
        // collect child's statistics by owner
        children.forEach {
            it.collect(id = name) { stat -> it.statisticsProvider.emit(stat) }
        }
    }

    override suspend fun start() {
        lifecycle.startOption = LifecycleStartOption.Start
        super.start()
    }

    override suspend fun resume() {
        if (isActive || state is State.FINISHED) return
        lifecycle.startOption = LifecycleStartOption.Resume
        startByOption(iterationFrom = iteration)
    }

    override suspend fun restart(forceStop: Boolean, resetPopulation: Boolean) {
        lifecycle.startOption = LifecycleStartOption.Restart(forceStop, resetPopulation)
        super.restart(forceStop, resetPopulation)
    }
}
