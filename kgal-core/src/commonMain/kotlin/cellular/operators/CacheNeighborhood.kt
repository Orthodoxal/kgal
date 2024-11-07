package kgal.cellular.operators

import kgal.Lifecycle
import kgal.cellular.CellularLifecycle
import kgal.cellular.CellularNeighborhood
import kgal.cellular.CellularPopulation
import kgal.utils.toroidalShapeIndicesFilter

internal const val IS_CACHE_NEIGHBORHOOD_ACTUAL = "IS_CACHE_NEIGHBORHOOD_ACTUAL"

/**
 * Cached neighborhood relevance flag. If `false` recalculate cache with [cacheNeighborhood].
 * @see [Lifecycle.store]
 */
public var CellularLifecycle<*, *>.isCacheNeighborhoodActual: Boolean
    get() = store[IS_CACHE_NEIGHBORHOOD_ACTUAL] as? Boolean ?: false
    set(value) {
        store[IS_CACHE_NEIGHBORHOOD_ACTUAL] = value
    }

/**
 * Recalculates neighborhood cache for [CellularNeighborhood] and [CellularPopulation.dimens] if [isCacheNeighborhoodActual] is `false`.
 * Then saves result to [CellularLifecycle.neighborsIndicesCache] and set [isCacheNeighborhoodActual] to `true`.
 *
 * Also note, it's use toroidal shape form.
 * @see toroidalShapeIndicesFilter
 */
public fun CellularLifecycle<*, *>.cacheNeighborhood() {
    if (!isCacheNeighborhoodActual) {
        val (indicesOneArray, indicesNArray) = neighborhood.neighborsIndicesMatrix(population.dimens)
        neighborsIndicesCache = Array(population.size) { position ->
            toroidalShapeIndicesFilter(position, population.dimens, indicesOneArray, indicesNArray)
        }
        isCacheNeighborhoodActual = true
    }
}