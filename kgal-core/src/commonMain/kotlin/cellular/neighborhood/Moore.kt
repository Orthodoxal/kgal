package kgal.cellular.neighborhood

import kgal.cellular.CellularNeighborhood
import kgal.cellular.Dimens
import kgal.utils.positionByCoordinatesInNArray

/**
 * Defines `Moore neighborhood` with [radius] > 0.
 *
 * Example for Moore neighborhood with [radius] = 1:
 * ```
 * X   X   X   X   X
 * X   N   N   N   X
 * X   N   C   N   X
 * X   N   N   N   X
 * X   X   X   X   X
 * ```
 * Where `C` - target chromosome, `N` - neighbors for current target chromosomes, `X` - other chromosomes in population.
 * @see <a href="https://en.wikipedia.org/wiki/Moore_neighborhood">Moore neighborhood</a>
 */
public data class Moore(
    val radius: Int
) : CellularNeighborhood {

    init {
        require(radius > 0) { "Radius must be positive." }
    }

    override fun neighborsCount(dimenCount: Int): Int {
        var result = radius
        repeat(dimenCount - 1) { result *= radius }
        return result
    }

    override fun neighborsIndicesMatrix(dimens: Dimens): Pair<IntArray, Array<IntArray>> {
        val dimenCount = dimens.count
        val radDimen = radius * 2 + 1
        var neighborsCount = radDimen
        repeat(dimenCount - 1) { neighborsCount *= radDimen }
        neighborsCount--

        val coordinates = IntArray(dimenCount) { -radius }
        val resultOneArray = IntArray(neighborsCount)
        val resultNArray = Array(neighborsCount) { IntArray(dimenCount) }
        var resultIndex = 0

        var dimenIndex = 0
        while (coordinates[dimenIndex] <= radius) {
            if (coordinates.all { it == 0 }) {
                coordinates[dimenIndex] = coordinates[dimenIndex] + 1
                continue
            }
            resultOneArray[resultIndex] = positionByCoordinatesInNArray(coordinates, dimens)
            resultNArray[resultIndex] = coordinates.copyOf()
            resultIndex++
            coordinates[dimenIndex] = coordinates[dimenIndex] + 1

            if (resultIndex == neighborsCount) return resultOneArray to resultNArray

            while (coordinates[dimenIndex] > radius) {
                coordinates[dimenIndex] = -radius
                dimenIndex++
                coordinates[dimenIndex] = coordinates[dimenIndex] + 1
            }
            dimenIndex = 0
        }
        return resultOneArray to resultNArray
    }
}
