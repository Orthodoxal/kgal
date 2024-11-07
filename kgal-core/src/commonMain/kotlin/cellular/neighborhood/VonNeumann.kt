package kgal.cellular.neighborhood

import kgal.cellular.CellularNeighborhood
import kgal.cellular.Dimens
import kgal.utils.delannoyNumber
import kgal.utils.positionByCoordinatesInNArray
import kotlin.math.abs

/**
 * Defines `Von Neumann neighborhood` with [radius] > 0.
 *
 * Example for Von Neumann neighborhood with [radius] = 1:
 * ```
 * X   X   X   X   X
 * X   X   N   X   X
 * X   N   C   N   X
 * X   X   N   X   X
 * X   X   X   X   X
 * ```
 * Where `C` - target chromosome, `N` - neighbors for current target chromosomes, `X` - other chromosomes in population.
 * @see <a href="https://en.wikipedia.org/wiki/Von_Neumann_neighborhood">Von Neumann neighborhood</a>
 */
public data class VonNeumann(
    val radius: Int
) : CellularNeighborhood {

    init {
        require(radius > 0) { "Radius must be positive." }
    }

    override fun neighborsCount(dimenCount: Int): Int = delannoyNumber(dimenCount, radius) - 1

    override fun neighborsIndicesMatrix(dimens: Dimens): Pair<IntArray, Array<IntArray>> {
        val dimenCount = dimens.count
        val count = neighborsCount(dimenCount)
        val resultOneArray = IntArray(count)
        val resultNArray = Array(count) { IntArray(dimenCount) }
        var resultIndex = 0

        val coordinates = IntArray(dimenCount)
        coordinates[0] = -radius
        val rCoordinates = IntArray(dimenCount)
        rCoordinates[0] = radius
        val isZero = BooleanArray(dimenCount - 1)

        var dimenIndex = 0
        if (dimenCount == 1) {
            var r = -radius
            repeat(count) { neighborIndex ->
                if (r == 0) r++
                resultOneArray[neighborIndex] = r
                resultNArray[neighborIndex] = intArrayOf(r)
                r++
            }
            return resultOneArray to resultNArray
        }

        while (coordinates[dimenIndex] <= rCoordinates[dimenIndex]) {
            val rCoordinateNew = rCoordinates[dimenIndex] - abs(coordinates[dimenIndex])
            if (dimenIndex == 0 || isZero[dimenIndex - 1]) {
                isZero[dimenIndex] = coordinates[dimenIndex] == 0
            }
            dimenIndex++
            coordinates[dimenIndex] = -rCoordinateNew
            rCoordinates[dimenIndex] = rCoordinateNew

            if (dimenIndex == dimenCount - 1) {
                while (coordinates[dimenIndex] <= rCoordinates[dimenIndex]) {
                    if (isZero.all { it } && coordinates[dimenIndex] == 0) {
                        coordinates[dimenIndex] = coordinates[dimenIndex] + 1
                        continue
                    }
                    resultOneArray[resultIndex] = positionByCoordinatesInNArray(coordinates, dimens)
                    resultNArray[resultIndex] = coordinates.copyOf()
                    resultIndex++
                    coordinates[dimenIndex] = coordinates[dimenIndex] + 1
                }

                while (coordinates[dimenIndex] >= rCoordinates[dimenIndex]) {
                    dimenIndex--
                    if (dimenIndex == -1) return resultOneArray to resultNArray
                }

                coordinates[dimenIndex] = coordinates[dimenIndex] + 1
            }
        }
        return resultOneArray to resultNArray
    }
}
