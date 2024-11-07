package kgal.utils

import kgal.cellular.Dimens

/**
 * Calculates a coordinate (index) in a one-dimensional array
 * that is a projection of an n-dimensional array along [coordinates].
 *
 * Example:
 * ```
 * val coordinates = intArrayOf(2, 1)
 * val dimens = Dimens.square(length = 3)
 *     0    1    2
 * 0  [0]  [1]  [2]
 * 1  [3]  [4]  [5]
 * 2  [6]  [7]  [8]
 * val coordinate = positionByCoordinatesInNArray(coordinates, dimens)
 * println(coordinate) // 5
 * ```
 * @param coordinates n coordinates (indices) of n-dimensional array
 * @param dimens dimensions for an n-array
 * @return coordinate (index) in a projection one-dimensional array
 * @see coordinatesInNArrayByPosition as an inverse function
 */
internal fun positionByCoordinatesInNArray(
    coordinates: IntArray,
    dimens: Dimens,
): Int {
    var pos = 0
    repeat(dimens.count) { n ->
        if (coordinates[n] >= dimens.value[n])
            throw IndexOutOfBoundsException(
                "Coordinate ${coordinates[n]} by index $n in coordinates: " +
                        "${coordinates.joinToString(prefix = "[", postfix = "]")} " +
                        "out of bounds for dimen with size ${dimens.value[n]} in dimens: " +
                        "${dimens.value.joinToString(prefix = "[", postfix = "]")}."
            )
        var multi = 1
        repeat(n) { i -> multi *= dimens.value[i] }
        pos += coordinates[n] * multi
    }
    return pos
}

/**
 * Calculates a coordinates (indices) in an n-dimensional array from a projection one-dimensional array.
 *
 * Example:
 * ```
 * val position = 5
 * val dimens = Dimens.square(length = 3)
 *     0    1    2
 * 0  [0]  [1]  [2]
 * 1  [3]  [4]  [5]
 * 2  [6]  [7]  [8]
 * val coordinates = coordinatesInNArrayByPosition(position, dimens)
 * println(coordinates.joinToString()) // 2, 1
 * ```
 * @param position coordinate (index) in a one-dimensional array
 * @param dimens dimensions for an n-array
 * @return coordinates (indices) in an n-dimensional array
 * @see positionByCoordinatesInNArray as an inverse function
 */
internal fun coordinatesInNArrayByPosition(
    position: Int,
    dimens: Dimens,
): IntArray = IntArray(dimens.count) { n ->
    var multi = 1
    repeat(n) { i -> multi *= dimens.value[i] }
    (position / multi) % dimens.value[n]
}

public fun main() {
    val coordinates = intArrayOf(3, 0)
    val dimens = Dimens.rectangle(4, 3)
    val coordinate = positionByCoordinatesInNArray(coordinates, dimens)
    println(coordinate)
    val coordinatesFrom = coordinatesInNArrayByPosition(coordinate, dimens)
    println(coordinatesFrom.joinToString())
}
