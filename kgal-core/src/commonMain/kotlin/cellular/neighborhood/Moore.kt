package kgal.cellular.neighborhood

import kgal.cellular.CellularNeighborhood
import kgal.cellular.Dimens
import kgal.utils.positionByCoordinatesInNArray

public data class Moore(override val radius: Int) : CellularNeighborhood {

    init {
        require(radius > 0) { "Radius must be positive." }
    }

    override fun neighboursCount(dimenCount: Int): Int {
        var result = radius
        repeat(dimenCount - 1) { result *= radius }
        return result
    }

    override fun neighboursIndicesMatrix(dimens: Dimens): Pair<IntArray, Array<IntArray>> {
        val dimenCount = dimens.count
        val radDimen = radius * 2 + 1
        var neighboursCount = radDimen
        repeat(dimenCount - 1) { neighboursCount *= radDimen }
        neighboursCount--

        val coordinates = IntArray(dimenCount) { -radius }
        val resultOneArray = IntArray(neighboursCount)
        val resultNArray = Array(neighboursCount) { IntArray(dimenCount) }
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

            if (resultIndex == neighboursCount) return resultOneArray to resultNArray

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
