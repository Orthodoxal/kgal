package kgal.panmictic.operators

import kgal.Population
import kgal.factory
import kgal.panmictic.PanmicticLifecycle

/**
 * If [Population] is not initialized -
 * Fills a [Population] with randomly generated Chromosomes by [Population.factory].
 */
public inline fun <V, F> PanmicticLifecycle<V, F>.fillPopulationIfNeed() {
    if (!population.initialized) {
        population.set(population = Array(population.maxSize) { random.factory() })
    }
}
