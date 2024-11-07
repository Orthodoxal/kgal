package kgal.utils

import kgal.cellular.CellularNeighborhood
import kgal.cellular.CellularPopulation
import kgal.cellular.Dimens

/**
 * Toroidal filter for neighbors indices.
 * @param position coordinate of target cell in a projection one-dimensional array
 * @param dimens dimens of current [CellularPopulation]
 * @param indicesOneArray initial matrix of neighbor's coordinates (indices) in a projection one-dimensional array
 * @param indicesNArray initial matrix of neighbor's coordinates (indices) in n-dimensional array
 * @return neighborsIndices in a projection one-dimensional array
 * @see <a href="https://en.wikipedia.org/wiki/Toroid">Toroidal shape</a>
 * @see CellularNeighborhood.neighborsIndicesMatrix
 */
internal fun toroidalShapeIndicesFilter(
    position: Int,
    dimens: Dimens,
    indicesOneArray: IntArray,
    indicesNArray: Array<IntArray>,
): IntArray {
    val positionCoordinatesNArray = coordinatesInNArrayByPosition(position, dimens)
    return IntArray(indicesOneArray.size) { neighborIndex ->
        val neighborCoordinatesNArray = IntArray(dimens.count) { dimenIndex ->
            positionCoordinatesNArray[dimenIndex] + indicesNArray[neighborIndex][dimenIndex]
        }

        var isNeedReEvaluate = false
        neighborCoordinatesNArray.forEachIndexed { index, coordinate ->
            val dimenSize = dimens.value[index]
            if (coordinate < 0) { // the coordinate is not negative
                isNeedReEvaluate = true
                neighborCoordinatesNArray[index] = dimenSize + coordinate
            } else if (coordinate >= dimenSize) { // coordinate is not greater than the dimension boundary
                isNeedReEvaluate = true
                neighborCoordinatesNArray[index] = coordinate % dimenSize
            }
        }

        if (isNeedReEvaluate) {
            positionByCoordinatesInNArray(neighborCoordinatesNArray, dimens)
        } else {
            position + indicesOneArray[neighborIndex]
        }
    }
}
