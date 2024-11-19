package kgal

import kgal.chromosome.Chromosome
import kgal.processor.parallelism.ParallelismConfig
import kgal.statistics.StatisticsConfig
import kgal.statistics.StatisticsProvider
import kgal.statistics.TimeMarker
import kgal.statistics.TimeStore
import kgal.statistics.note.StatisticNote
import kotlin.random.Random

/**
 * [EvolveScope] - base interface for evolution scope accompanying genetic evolution of [GA].
 * Implementations can add custom parameters specific to GAs.
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * [EvolveScope] has two main purposes:
 * - Safe transport layer between the [GA] and the evolutionary process, implemented in a scope:
 * [GA] ↔ [EvolveScope] ↔ evolution function
 * - Container for the evolutionary process, which is used to synchronize and organize the work of the genetic algorithm:
 * All genetic operators are performed in the scope area of EvolveScope,
 * which determines their specific implementation with [parallelismConfig], [store] and custom parameters.
 *
 * Each GA has its own evolution scope [EvolveScope].
 * To create your own implementation, it is recommended to use [AbstractEvolveScope].
 * @see AbstractEvolveScope
 */
public interface EvolveScope<V, F> {

    /**
     * Random associated with [GA]. Defines a pseudorandom number generator for predictive calculations.
     */
    public val random: Random

    /**
     * Population configuration for genetic algorithm.
     */
    public val population: Population<V, F>

    /**
     * Current iteration of genetic algorithm.
     */
    public val iteration: Int

    /**
     * Fitness function - a function that evaluates the quality or "fitness" of each individual (chromosome) in a population.
     * The fitness function determines how well a particular solution matches the target problem.
     * It can be changed.
     */
    public var fitnessFunction: (V) -> F

    /**
     * Store parallelism configuration for [GA]. Determines genetic operators process.
     * @see ParallelismConfig
     */
    public val parallelismConfig: ParallelismConfig

    /**
     * Statistics configuration associated with [GA].
     * @see [StatisticsConfig]
     */
    public val statisticsConfig: StatisticsConfig

    /**
     * Flag to stop evolution process cause stop condition has been worked.
     */
    public var finishByStopConditions: Boolean

    /**
     * Flag to stop evolution process cause the max iteration has been reached.
     */
    public var finishedByMaxIteration: Boolean

    /**
     * Store serves to synchronize between iterations of [GA].
     */
    public val store: MutableMap<String, Any?>

    /**
     * Store for all [TimeMarker]s of [GA].
     */
    public val timeStore: TimeStore

    /**
     * Emit [StatisticNote] to [StatisticsProvider].
     */
    public suspend fun emitStat(value: StatisticNote<Any?>)
}

/**
 * Population Name of [GA].
 */
public inline val EvolveScope<*, *>.name: String
    get() = population.name

/**
 * Population Size of [GA].
 */
public inline val EvolveScope<*, *>.size: Int
    get() = population.size

/**
 * Population Factory of [GA].
 */
public inline val <V, F> EvolveScope<V, F>.factory: PopulationFactory<V, F>
    get() = population.factory
