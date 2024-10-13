package kgal.operators

import kgal.Lifecycle
import kgal.Population
import kgal.reset
import kgal.utils.moreOrEquals

/**
 * Shake [percent] population with [Population.reset] function
 */
public inline fun <V, F> Lifecycle<V, F>.shake(
    percent: Double,
) {
    if (percent moreOrEquals 1.0) {
        population.reset(random)
    } else {
        val count = (percent * population.size).toInt()
        val start = population.size - count
        population.reset(random, start, population.size)
    }
}

/**
 * Shake [percent] population with [Population.reset] function if [predicate] is true
 */
public inline fun <V, F, L : Lifecycle<V, F>> L.shakeBy(
    percent: Double,
    predicate: L.() -> Boolean,
) {
    if (predicate(this)) {
        shake(percent)
    }
}
