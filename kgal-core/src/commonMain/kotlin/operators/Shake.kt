package kgal.operators

import kgal.EvolveScope
import kgal.Population
import kgal.reset
import kgal.size
import kgal.utils.moreOrEquals

/**
 * Shake [percent]*100% population with [Population.reset] function.
 * @param percent in range 0.0..1.0
 * @return range of shaking
 */
public inline fun <V, F> EvolveScope<V, F>.shake(
    percent: Double,
): Pair<Int, Int> {
    return if (percent moreOrEquals 1.0) {
        population.reset(random)
        0 to size
    } else {
        val count = (percent * size).toInt()
        val start = size - count
        shake(start, size)
        start to size
    }
}

/**
 * Shake population with [Population.reset] function.
 * @param from index from shaking start (inclusive)
 * @param to index to shaking end (exclusive)
 */
public fun <V, F> EvolveScope<V, F>.shake(from: Int, to: Int) {
    population.reset(random, from, to)
}

/**
 * Shake population with [Population.reset] function if [predicate] is true.
 * @param from index from shaking start (inclusive)
 * @param to index to shaking end (exclusive)
 * @param evaluateNew if true (default) evaluates new chromosome after shaking
 * @param parallelismLimit limit of parallel workers (for evaluation)
 * @param fitnessFunction fitnessFunction for evaluation
 */
public suspend inline fun <V, F, ES : EvolveScope<V, F>> ES.shakeBy(
    from: Int,
    to: Int,
    evaluateNew: Boolean = true,
    parallelismLimit: Int = parallelismConfig.workersCount,
    noinline fitnessFunction: (V) -> F = this.fitnessFunction,
    predicate: ES.() -> Boolean,
) {
    if (predicate(this)) {
        shake(from, to)

        if (evaluateNew) {
            evaluateAll(
                start = from,
                end = to,
                parallelismLimit = parallelismLimit,
                fitnessFunction = fitnessFunction,
            )
        }
    }
}

/**
 * Shake [percent]*100% population with [Population.reset] function if [predicate] is true.
 * @param percent in range 0.0..1.0
 * @param evaluateNew if true (default) evaluates new chromosome after shaking
 * @param parallelismLimit limit of parallel workers (for evaluation)
 * @param fitnessFunction fitnessFunction for evaluation
 */
public suspend inline fun <V, F, ES : EvolveScope<V, F>> ES.shakeBy(
    percent: Double,
    evaluateNew: Boolean = true,
    parallelismLimit: Int = parallelismConfig.workersCount,
    noinline fitnessFunction: (V) -> F = this.fitnessFunction,
    predicate: ES.() -> Boolean,
) {
    if (predicate(this)) {
        val (from, to) = shake(percent)

        if (evaluateNew) {
            evaluateAll(
                start = from,
                end = to,
                parallelismLimit = parallelismLimit,
                fitnessFunction = fitnessFunction,
            )
        }
    }
}
