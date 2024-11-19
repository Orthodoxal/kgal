package kgal.statistics.stats

import kgal.EvolveScope
import kgal.GA
import kgal.statistics.Session
import kgal.statistics.TimeMarker
import kgal.statistics.TimeStore
import kgal.statistics.note.Statistic
import kgal.statistics.timeMarker
import kotlin.time.Duration


private const val TIME_ITERATION = "TIME_ITERATION"

/**
 * [Duration] between the first STARTED and last value of timeStore
 * @see activeTimeTotal for only subsessions included
 */
public val <V, F> GA<V, F>.timeTotal: Duration
    get() {
        val started = timeStore.onStarted.firstOrNull() ?: return Duration.ZERO // NOT STARTED
        val last = timeStore.onFinished.lastOrNull()
            .latest(timeStore.onStopped.lastOrNull())
            .latest(timeStore.onIteration.lastOrNull())
            .latest(timeStore.onStarted.lastOrNull())
        return last?.let { it - started } ?: return Duration.ZERO // NO INFORMATION
    }

/**
 * [Duration] between all subsessions of [Session].
 *
 * For example:
 * ```
 * STARTED
 * STOPPED (5 seconds) - included
 * waiting for 10 seconds - not included
 * STARTED
 * FINISHED (3 seconds) - included
 * val activeTime = activeTimeTotal // 8 seconds
 * ```
 */
public val <V, F> GA<V, F>.activeTimeTotal: Duration
    get() {
        var activeTimeTotal = Duration.ZERO
        var startedIndex = 0
        var stoppedIndex = 0
        var finishedIndex = 0
        var started: TimeMarker? = timeStore.onStarted.firstOrNull() ?: return Duration.ZERO // NOT STARTED
        while (started != null) {
            val stopped = timeStore.onStopped.getOrNull(stoppedIndex++)
            if (stopped != null) {
                activeTimeTotal += stopped - started
            } else {
                val last = timeStore.onFinished.getOrNull(finishedIndex++) ?: timeStore.onIteration.lastOrNull()
                if (last != null) {
                    activeTimeTotal += last - started
                }
            }
            started = timeStore.onStarted.getOrNull(++startedIndex)
        }
        return activeTimeTotal
    }

/**
 * [Duration] between current and previous iteration.
 *
 * - NOTE! this is automatically adding iterationTimeMarker to [TimeStore]. Use only once in evolution strategy:
 * ```
 * evolve {
 *     // other genetic operators
 *     println(timeIteration) // OR stat(timeIteration()) for collecting to statistics
 * }
 * ```
 */
public val <V, F> EvolveScope<V, F>.timeIteration: Duration?
    get() {
        val previousIterationTimeMarker = timeStore.onIteration.lastOrNull()
        val iterationTimeMarker = timeMarker
        timeStore.onIteration.add(iterationTimeMarker)
        return previousIterationTimeMarker?.let { iterationTimeMarker - it }
    }

/**
 * Creates [Statistic] for current [timeIteration].
 */
public fun EvolveScope<*, *>.timeIteration(): Statistic<Duration?> = Statistic(TIME_ITERATION, timeIteration)

private inline fun TimeMarker?.latest(candidate: TimeMarker?): TimeMarker? {
    if (this == null) return candidate
    if (candidate == null) return this
    return if (candidate > this) candidate else this
}
