package kgal.cellular

import kotlin.jvm.JvmInline

/**
 * [Dimens] describes the dimensions of n-dimensional space of a [CellularPopulation] for [CellularGA].
 *
 * For example:
 * ```
 * val dimens = Dimens.rectangle(length = 4, width = 3)
 *     0    1    2    3
 * 0  [0]  [1]  [2]  [3]
 * 1  [4]  [5]  [6]  [7]
 * 2  [8]  [9]  [10] [11]
 * dimens.size = 12 // size of population
 * dimens.count = 2 // rectangle is two-dimensional space
 * ```
 * @see CellularPopulation
 */
@JvmInline
public value class Dimens(
    public val value: IntArray,
) {

    /**
     * Size of n-dimensional space, where n > 0.
     *
     * For example: value = intArrayOf(5, 6) -> size = 5 * 6 = 30
     *
     * This property does not have a backing field. Consider caching a property in case of repeated use.
     */
    public val size: Int get() = value.fold(1) { acc, dimen -> acc * dimen }

    /**
     * Count of dimensions
     */
    public val count: Int get() = value.size

    public companion object Factory {

        /**
         * Creates a one-dimensional (1D) array (line):
         * ```
         * val dimens = Dimens.square(length = 4)
         *     0    1    2    3
         *    [0]  [1]  [2]  [3]
         *
         * dimens.size = 4 // size of population
         * dimens.count = 1 // line is one-dimensional space
         * ```
         */
        public fun line(length: Int): Dimens = Dimens(
            value = intArrayOf(length),
        )

        /**
         * Creates a two-dimensional (2D) array (square):
         * ```
         * val dimens = Dimens.square(length = 4)
         *     0    1    2    3
         * 0  [0]  [1]  [2]  [3]
         * 1  [4]  [5]  [6]  [7]
         * 2  [8]  [9]  [10] [11]
         * 2  [8]  [9]  [10] [11]
         * 3  [12] [13] [14] [15]
         *
         * dimens.size = 16 // size of population
         * dimens.count = 2 // square is two-dimensional space
         * ```
         */
        public fun square(length: Int): Dimens = Dimens(
            value = intArrayOf(length, length),
        )

        /**
         * Creates a two-dimensional (2D) array (rectangle):
         * ```
         * val dimens = Dimens.rectangle(length = 4, width = 3)
         *     0    1    2    3
         * 0  [0]  [1]  [2]  [3]
         * 1  [4]  [5]  [6]  [7]
         * 2  [8]  [9]  [10] [11]
         *
         * dimens.size = 12 // size of population
         * dimens.count = 2 // rectangle is two-dimensional space
         * ```
         */
        public fun rectangle(length: Int, width: Int): Dimens = Dimens(
            value = intArrayOf(length, width),
        )

        /**
         * Creates a three-dimensional (3D) array (cube):
         * ```
         * val dimens = Dimens.cube(length = 4)
         * dimens.size = 64 // size of population
         * dimens.count = 3 // cube is three-dimensional space
         * ```
         */
        public fun cube(length: Int): Dimens = Dimens(
            value = intArrayOf(length, length, length),
        )

        /**
         * Creates a three-dimensional (3D) array (parallelepiped):
         * ```
         * val dimens = Dimens
         *     .parallelepiped(length = 4, width = 3, height = 2)
         * dimens.size = 24 // size of population
         * dimens.count = 3 // parallelepiped is three-dimensional space
         * ```
         */
        public fun parallelepiped(length: Int, width: Int, height: Int): Dimens = Dimens(
            value = intArrayOf(length, width, height),
        )

        /**
         * Creates custom a n-dimensional (n-D) array:
         * ```
         * val dimens = Dimens.custom(5, 3, 4, 9, 6)
         * dimens.size = 3240 // size of population
         * dimens.count = 5 // n-dimensional space
         * ```
         */
        public fun custom(vararg dimens: Int): Dimens = Dimens(dimens)
    }
}
