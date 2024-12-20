package kgal.panmictic

import kgal.AbstractGA
import kgal.GA
import kgal.StopPolicy
import kgal.chromosome.Chromosome
import kgal.panmictic.operators.evaluation

/**
 * [PanmicticGA] - best known as the classical genetic algorithm (pGA).
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
 * @see <a href="https://en.wikipedia.org/wiki/Genetic_algorithm">Panmictic Genetic Algorithm</a>
 * @see PanmicticPopulation
 * @see PanmicticEvolveScope
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
     * @see [PanmicticEvolveScope.evaluation]
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
 * Immediately restart genetic algorithm.
 * @param populationSize new size of restarted population
 * @param populationBuffer new buffer of restarted population
 * @param forceStop - if `true` and [GA.isActive] stop [GA] with [StopPolicy.Immediately] else [StopPolicy.Default]
 * @param resetPopulation if true all progress will be lost.
 */
public suspend fun <V, F> PanmicticGA<V, F>.restart(
    populationSize: Int,
    populationBuffer: Int,
    forceStop: Boolean = false,
    resetPopulation: Boolean = true,
) {
    if (isActive) {
        stop(stopPolicy = if (forceStop) StopPolicy.Immediately else StopPolicy.Default)
    }
    population.resize(populationSize, populationBuffer)
    restart(forceStop, resetPopulation)
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
 * @param fitnessFunction fitness function for evaluation step
 * @param config scope function to initialize [PanmicticGA]
 *
 * @see <a href="https://en.wikipedia.org/wiki/Genetic_algorithm">Panmictic Genetic Algorithm</a>
 * @see PanmicticGA
 * @see PanmicticPopulation
 * @see PanmicticEvolveScope
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
 * @param configuration for initialization [PanmicticGA]
 * @see AbstractGA
 */
internal class PanmicticGAInstance<V, F>(
    configuration: PanmicticConfig<V, F>,
) : PanmicticGA<V, F>, AbstractGA<V, F, PanmicticEvolveScope<V, F>>(configuration) {

    override val population: PanmicticPopulation<V, F> = configuration.population

    override val evolveScope: PanmicticEvolveScope<V, F> by lazy { PanmicticEvolveScope(this, configuration) }

    override var elitism: Int = configuration.elitism
}
