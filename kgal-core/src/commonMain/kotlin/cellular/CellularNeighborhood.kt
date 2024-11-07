package kgal.cellular

import kgal.cellular.neighborhood.Moore
import kgal.cellular.neighborhood.VonNeumann
import kgal.utils.toroidalShapeIndicesFilter

/**
 * [CellularNeighborhood] - basic interface defining neighborhood parameters for [CellularGA].
 * @see Moore
 * @see VonNeumann
 */
public interface CellularNeighborhood {

    /**
     * Calculates the neighbor chromosomes count depending on the dimension count of [CellularPopulation.dimens].
     *
     * Assumes that the space of neighbors does not intersect with itself:
     * the identified neighbors must form a `set` (a set of unique elements without duplication).
     * Also, that means neighbors indices from `neighborsIndicesMatrix.first` must contain only unique elements.
     * @see Dimens
     */
    public fun neighborsCount(dimenCount: Int): Int

    /**
     * Calculates universal matrix for neighbor's indices depending on the [CellularPopulation.dimens].
     * To calculate actual coordinates of neighbors for a target cell,
     * need to sum coordinates from the universal matrix with the target cell coordinates.
     *
     * `NOTE` Be careful, before applying the calculated coordinates of neighbors they must go through a [toroidalShapeIndicesFilter].
     *
     * Example if usage universal matrix for calculating neighbor's indices for different target cells:
     * ```
     * val moore = VonNeumann(radius = 1)
     * val dimens = Dimens.square(length = 3)
     * val (indicesOneArray, indicesNArray) = moore.neighborsIndicesMatrix(dimens)
     * println("Universal matrix in a projection one-dimensional array: ${indicesOneArray.joinToString()}")
     * println("Universal matrix in n-dimensional array: ${indicesNArray.joinToString { "(${it.joinToString()})" }}")
     * val actualNeighborsPosition2 = toroidalShapeIndicesFilter(
     *     position = 2,
     *     dimens = dimens,
     *     indicesOneArray = indicesOneArray,
     *     indicesNArray = indicesNArray,
     * )
     * val actualNeighborsPosition3 = toroidalShapeIndicesFilter(
     *     position = 3,
     *     dimens = dimens,
     *     indicesOneArray = indicesOneArray,
     *     indicesNArray = indicesNArray,
     * )
     * println("Actual neighbors indices for position 2: ${actualNeighborsPosition2.joinToString()}")
     * println("Actual neighbors indices for position 3: ${actualNeighborsPosition3.joinToString()}")
     *
     * // Console:
     * Universal matrix in a projection one-dimensional array: -1, -3, 3, 1
     * Universal matrix in n-dimensional array: (-1, 0), (0, -1), (0, 1), (1, 0)
     * Actual neighbors indices for position 2: 1, 8, 5, 0
     * Actual neighbors indices for position 3: 5, 0, 6, 4
     * ```
     * @return pair of neighbors indices where:
     * - first - initial neighbors coordinates (indices) in a projection one-dimensional array of [CellularPopulation]
     * - second - initial neighbors coordinates (indices) in an n-dimensional array of [CellularPopulation]
     * @see toroidalShapeIndicesFilter
     */
    public fun neighborsIndicesMatrix(dimens: Dimens): Pair<IntArray, Array<IntArray>>
}
