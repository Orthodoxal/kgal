package kgal.operators

import kgal.GA
import kgal.Lifecycle

/**
 * Stop and finish [GA] if iteration more or equal to [maxIteration].
 */
public inline fun <V, F> Lifecycle<V, F>.stopBy(
    maxIteration: Int = Int.MAX_VALUE,
) {
    if (iteration >= maxIteration) {
        finishedByMaxIteration = true
    }
}

/**
 * Stop and finish [GA] if [stopCondition] return true.
 */
public inline fun <V, F> Lifecycle<V, F>.stopBy(
    stopCondition: Lifecycle<V, F>.() -> Boolean,
) {
    if (stopCondition()) {
        finishByStopConditions = true
    }
}

/**
 * Stop and finish [GA] if:
 * - iteration more or equal to [maxIteration]
 * - [stopCondition] return true
 */
public inline fun <V, F> Lifecycle<V, F>.stopBy(
    maxIteration: Int = Int.MAX_VALUE,
    stopCondition: Lifecycle<V, F>.() -> Boolean,
) {
    stopBy(maxIteration)
    stopBy(stopCondition)
}
