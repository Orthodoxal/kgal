package kgal.panmictic

import kgal.AbstractGA
import kgal.GA
import kgal.chromosome.Chromosome
import kgal.panmictic.operators.evaluation

/**
 * [PanmicticGA] - best known as the classical genetic algorithm.
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * [PanmicticGA] is a heuristic search algorithm used to solve optimization and modeling problems by randomly selecting, combining,
 * and varying the desired parameters using mechanisms similar to natural selection in nature.
 * It includes a [PanmicticPopulation] (array) of homogeneous chromosomes.
 * The evolution strategy is extended to a given population, changing its contents during the operation of the GA.
 *
 * Creates with Kotlin DSL by [pGA].
 * @see PanmicticPopulation
 * @see PanmicticLifecycle
 * @see PanmicticConfig
 */
public interface PanmicticGA<V, F> : GA<V, F> {

    /**
     * Population of homogeneous chromosomes for [PanmicticGA].
     */
    override val population: PanmicticPopulation<V, F>

    /**
     * Number of elite chromosomes - the best chromosomes of the population, which have privileged rights:
     * - recalculates on each evaluation stage and moved to start of [PanmicticPopulation]
     * - guaranteed to pass the selection stage
     * - at the crossing stage they cannot be changed or replaced,
     * but they actively participate in the creation of a new generation by changing other chromosomes
     * - do not change during the mutation stage
     * @see [PanmicticLifecycle.evaluation]
     */
    public var elitism: Int

    public companion object {

        /**
         * Creates [PanmicticGA] with [configuration].
         */
        public fun <V, F> create(
            configuration: PanmicticConfig<V, F>,
        ): PanmicticGA<V, F> = PanmicticGAInstance(configuration)
    }
}

/**
 * Creates [PanmicticGA] with Kotlin DSL.
 *
 * Example for OneMax task:
 * ```
 * pGA(
 *     // Set a population configuration
 *     population = population(size = 200) { booleans(size = 100) },
 *     // Set a fitness function
 *     fitnessFunction = { value -> value.count { it } },
 * ) {
 *     random = Random(seed = 42) // set pseudo random number generator
 *     elitism = 10 // set elitism
 *
 *     before {
 *         println("GA STARTED, Init population: $population")
 *     }
 *
 *     // create evolution strategy
 *     evolve {
 *         selTournament(size = 3, parallelismLimit = NO_PARALLELISM) // select
 *         cxOnePoint(chance = 0.8) // crossover
 *         mutFlipBit(chance = 0.2, flipBitChance = 0.01) // mutate
 *         evaluation() // evaluate population
 *         stopBy(maxIteration = 50) { bestFitness == 100 } // finish GA by conditions
 *     }
 *
 *     after {
 *         println("GA FINISHED, Result = $best")
 *     }
 * }.startBlocking()
 * ```
 * @param population population of [PanmicticGA]
 * @param fitnessFunction fitness function for evaluation step of [PanmicticGA]
 * @param config scope function to initialize [PanmicticGA]
 *
 * @see PanmicticGA
 * @see PanmicticPopulation
 * @see PanmicticLifecycle
 * @see PanmicticConfig
 */
public inline fun <V, F> pGA(
    population: PanmicticPopulation<V, F>,
    noinline fitnessFunction: (V) -> F,
    config: PanmicticConfigScope<V, F>.() -> Unit,
): PanmicticGA<V, F> =
    PanmicticGA.create(
        configuration = PanmicticConfigScope(population, fitnessFunction).apply(config)
    )

/**
 * Base realization of [PanmicticGA].
 * @param configuration configuration for initialization [PanmicticGA]
 * @see AbstractGA
 */
internal class PanmicticGAInstance<V, F>(
    configuration: PanmicticConfig<V, F>,
) : PanmicticGA<V, F>, AbstractGA<V, F, PanmicticLifecycle<V, F>>(configuration) {

    override val population: PanmicticPopulation<V, F> = configuration.population

    override val lifecycle: PanmicticLifecycle<V, F> by lazy {
        PanmicticLifecycle(this, configuration.parallelismConfig, configuration.statisticsConfig)
    }

    override var elitism: Int = configuration.elitism
}
