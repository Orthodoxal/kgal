package kgal

import kgal.processor.parallelism.ParallelismConfig
import kgal.statistics.StatisticsConfig
import kgal.statistics.TimeStore
import kgal.statistics.note.StatisticNote
import kotlin.random.Random

/**
 * [AbstractLifecycle] - abstract class for base implementation of [Lifecycle].
 * @param ga owner [GA] for this Lifecycle.
 * @param config base [GA] configuration
 */
public abstract class AbstractLifecycle<V, F>(
    private val ga: GA<V, F>,
    config: Config<V, F, *>,
) : Lifecycle<V, F> {
    override val random: Random get() = ga.random

    override val iteration: Int
        get() = ga.iteration

    override var fitnessFunction: (V) -> F
        get() = ga.fitnessFunction
        set(value) {
            ga.fitnessFunction = value
        }

    override val store: MutableMap<String, Any?> = mutableMapOf()

    override val parallelismConfig: ParallelismConfig = config.parallelismConfig

    override val statisticsConfig: StatisticsConfig = config.statisticsConfig

    override val timeStore: TimeStore get() = ga.timeStore

    override var finishByStopConditions: Boolean = false
    override var finishedByMaxIteration: Boolean = false

    override suspend fun emitStat(value: StatisticNote<Any?>): Unit = ga.statisticsProvider.emit(value)
}
