package kgal.operators

import kgal.Lifecycle
import kgal.Population
import kgal.reset

/**
 * If [Population] is not initialized -
 * Fills a [Population] with randomly generated Chromosomes by [Population.reset].
 */
public inline fun <V, F> Lifecycle<V, F>.fillPopulationIfNeed() {
    if (!population.initialized) {
        population.reset(random)
    }
}
