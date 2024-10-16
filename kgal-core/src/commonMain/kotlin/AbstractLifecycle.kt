package kgal

import kgal.statistics.note.StatisticNote
import kotlin.random.Random

/**
 * [AbstractLifecycle] - abstract class for base implementation of [Lifecycle].
 * @param ga owner [GA] for this Lifecycle.
 */
public abstract class AbstractLifecycle<V, F>(
    private val ga: GA<V, F>,
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

    override var finishByStopConditions: Boolean = false
    override var finishedByMaxIteration: Boolean = false

    override suspend fun emitStat(value: StatisticNote<Any?>): Unit = ga.statisticsProvider.emit(value)
}
