package kgal.statistics

import kgal.GA
import kgal.State
import kotlin.time.TimeSource

/**
 * Short typealias for [TimeSource.Monotonic.ValueTimeMark].
 */
public typealias TimeMarker = TimeSource.Monotonic.ValueTimeMark

/**
 * Store for all [TimeMarker]s of [GA].
 * @property onStarted all time markers for launches by [GA.start], [GA.resume] and [GA.restart]
 * @property onIteration all time markers for iterations
 * @property onFinished all time markers for [State.FINISHED] of [GA]
 * @property onStopped all time markers for [State.STOPPED] of [GA]
 */
public data class TimeStore(
    val onStarted: MutableList<TimeMarker>,
    val onIteration: MutableList<TimeMarker>,
    val onFinished: MutableList<TimeMarker>,
    val onStopped: MutableList<TimeMarker>,
) {

    /**
     * Creates [TimeStore] with default sizes.
     */
    public constructor(
        startedSize: Int = 10,
        onIterationSize: Int = 200,
        finishedSize: Int = 10,
        stoppedSize: Int = 10,
    ) : this(
        onStarted = ArrayList(startedSize),
        onIteration = ArrayList(onIterationSize),
        onFinished = ArrayList(finishedSize),
        onStopped = ArrayList(stoppedSize),
    )
}

/**
 * Clear all time markers.
 */
public fun TimeStore.clear() {
    onStarted.clear()
    onIteration.clear()
    onFinished.clear()
    onStopped.clear()
}

/**
 * Creates [TimeMarker] with [TimeSource.Monotonic.markNow]
 */
internal val timeMarker: TimeMarker get() = TimeSource.Monotonic.markNow()
