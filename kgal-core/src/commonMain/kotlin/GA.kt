package kgal

import kgal.chromosome.Chromosome
import kgal.processor.parallelism.ParallelismConfig
import kgal.statistics.STAT_COLLECTOR
import kgal.statistics.StatisticsProvider
import kgal.statistics.StatisticsProvider.Companion.BASE_COLLECTOR_ID
import kgal.statistics.TimeMarker
import kgal.statistics.TimeStore
import kgal.statistics.note.StatisticNote
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

/**
 * [GA] - base interface for controlling the genetic algorithm.
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * [kgal] understands genetic algorithm with [Lifecycle] abstraction, see it to understand how it works deeply.
 *
 * [GA] implementations are powered by [kotlin.coroutines],
 * which makes it easy to organize safe parallel operation of the genetic algorithm.
 * [GA] uses suspended control methods and [CoroutineContext] binding, which allows you not to worry about memory leaks.
 *
 * Base recommendations:
 * - Create with TODO(Дописать методы для создания)
 * - Use [GA.startBlocking] or [GA.start] to run Genetic Algorithm
 * - Use [GA.collect] for collecting [StatisticNote] produced by [statisticsProvider]
 * - Look for [ParallelismConfig] to create parallel [GA]. Note that parallelism may not always improve performance.
 * Using parallelism is recommended only for high-performance computing
 *
 * To create your own implementation, it is recommended to use [AbstractGA],
 * also pay attention to [AbstractLifecycle] and [AbstractConfigScope] with Kotlin DSL style.
 *
 * @see [State]
 * @see [AbstractGA]
 * @see [Lifecycle]
 * @see [Population]
 * @see [Chromosome]
 * @see [StatisticsProvider]
 * @see [ParallelismConfig]
 */
public interface GA<V, F> {

    /**
     * State describes the current state of the genetic algorithm.
     */
    public val state: State

    /**
     * Return `true` if [GA] is active.
     */
    public val isActive: Boolean

    /**
     * Population configuration for genetic algorithm.
     */
    public val population: Population<V, F>

    /**
     * Fitness function - a function that evaluates the quality or "fitness" of each individual (chromosome) in a population.
     * The fitness function determines how well a particular solution matches the target problem.
     * It can be changed.
     */
    public var fitnessFunction: (V) -> F

    /**
     * Random associated with [GA]. Defines a pseudorandom number generator for predictive calculations.
     */
    public val random: Random

    /**
     * Current iteration of genetic algorithm.
     */
    public val iteration: Int

    /**
     * Statistics configuration associated with [GA].
     */
    public val statisticsProvider: StatisticsProvider

    /**
     * Store for all [TimeMarker]s of [GA].
     */
    public val timeStore: TimeStore

    /**
     * Start genetic algorithm.
     * @throws [IllegalStateException] if [isActive] true ([GA] can only work in one coroutineScope at a time)
     */
    public suspend fun start()

    /**
     * Resume stopped genetic algorithm.
     *
     * Returns (Resume will not be executed) if [GA.isActive] OR [state] is [State.FINISHED].
     */
    public suspend fun resume()

    /**
     * Immediately restart genetic algorithm.
     *
     * Equals to [start] if [state] is [State.INITIALIZED].
     * @param forceStop - if `true` and [GA] is active stop [GA] with [StopPolicy.Immediately] else [StopPolicy.Default]
     * @param resetPopulation if `true` all progress will be lost.
     */
    public suspend fun restart(
        forceStop: Boolean = false,
        resetPopulation: Boolean = true,
    )

    /**
     * Stop genetic algorithm.
     * @param stopPolicy how [GA] should be stopped
     * @see [StopPolicy]
     */
    public suspend fun stop(stopPolicy: StopPolicy = StopPolicy.Default)
}

/**
 * Allows you to collect statistical data using reactive programming by [kotlinx.coroutines.flow].
 * @param id unique id for collector of [GA.statisticsProvider]
 * @param collector yours collector for [StatisticNote].
 */
public fun <V, F> GA<V, F>.collect(
    id: String = BASE_COLLECTOR_ID,
    collector: STAT_COLLECTOR,
): GA<V, F> = apply {
    statisticsProvider.collect(id) { flow -> flow.collect(collector) }
}

/**
 * Unsubscribe and remove collector by [id].
 */
public suspend inline fun GA<*, *>.removeCollector(id: String) {
    statisticsProvider.removeCollector(id)
}

/**
 * Unsubscribe and clear all collectors for [StatisticsProvider].
 */
public suspend inline fun GA<*, *>.clearCollectors() {
    statisticsProvider.clearCollectors()
}

/**
 * Start [GA] with [runBlocking], non suspend function.
 * @param context [CoroutineContext] for genetic algorithm execution
 */
public fun GA<*, *>.startBlocking(context: CoroutineContext = EmptyCoroutineContext): Unit =
    runBlocking(context) { start() }

/**
 * Population Name of [GA].
 */
public inline val GA<*, *>.name: String get() = population.name

/**
 * Population Size of [GA].
 */
public inline val GA<*, *>.size: Int get() = population.size

/**
 * Population Factory of [GA].
 */
public inline val <V, F> GA<V, F>.factory: PopulationFactory<V, F> get() = population.factory
