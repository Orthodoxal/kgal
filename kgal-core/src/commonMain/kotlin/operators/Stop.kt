package kgal.operators

import kgal.EvolveScope
import kgal.GA

/**
 * Stop and finish [GA] if iteration more or equal to [maxIteration].
 */
public inline fun <V, F> EvolveScope<V, F>.stopBy(
    maxIteration: Int = Int.MAX_VALUE,
) {
    if (iteration >= maxIteration) {
        finishedByMaxIteration = true
    }
}

/**
 * Stop and finish [GA] if [stopCondition] return true.
 */
public inline fun <V, F> EvolveScope<V, F>.stopBy(
    stopCondition: EvolveScope<V, F>.() -> Boolean,
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
public inline fun <V, F> EvolveScope<V, F>.stopBy(
    maxIteration: Int = Int.MAX_VALUE,
    stopCondition: EvolveScope<V, F>.() -> Boolean,
) {
    stopBy(maxIteration)
    stopBy(stopCondition)
}
