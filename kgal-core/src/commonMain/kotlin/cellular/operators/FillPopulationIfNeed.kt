package kgal.cellular.operators

import kgal.Population
import kgal.cellular.CellularEvolveScope
import kgal.factory

/**
 * If [Population] is not initialized -
 * Fills a [Population] with randomly generated Chromosomes by [Population.factory].
 */
public inline fun <V, F> CellularEvolveScope<V, F>.fillPopulationIfNeed() {
    if (!population.initialized) {
        population.set(population = Array(population.size) { random.factory() })
    }
}
