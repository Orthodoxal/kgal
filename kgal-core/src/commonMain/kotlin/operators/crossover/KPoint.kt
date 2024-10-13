package kgal.operators.crossover

import kgal.utils.indicesByRandom
import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun <T> crossoverKPoint(value1: Array<T>, value2: Array<T>, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: BooleanArray, value2: BooleanArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: ByteArray, value2: ByteArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: CharArray, value2: CharArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: DoubleArray, value2: DoubleArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: FloatArray, value2: FloatArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: IntArray, value2: IntArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: LongArray, value2: LongArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun <T> crossoverKPoint(value1: MutableList<T>, value2: MutableList<T>, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Executes a K point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Count (K points) = 2
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 1, 5
 * Child 1: ++----++
 * Child 2: --++++--
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param count k-points for crossover
 */
public fun crossoverKPoint(value1: ShortArray, value2: ShortArray, count: Int, random: Random): Unit =
    geneSwap(value1, value2, multiCrossIndices(value1.size, count, random))

/**
 * Choose unique random indices for K-point crossover
 * @param size size of [Chromosome.value]
 * @param count number of K-points
 */
internal fun multiCrossIndices(size: Int, count: Int, random: Random): IntArray {
    if (count < 1) throw UnsupportedOperationException("Count must be more than 0")
    return IntRange(1, size - 1).indicesByRandom(count, random)
}

/**
 * Optimized multi swap strategy. Choose the way to swap indices with minimal count of operations.
 * Make swap by [swapper]
 */
internal inline fun multiSwapStrategy(size: Int, crossIndices: IntArray, swapper: (swapIndex: Int) -> Unit) {
    if (crossIndices.size == 1) {
        swapStrategy(size, crossIndices[0], swapper)
        return
    }

    var evenSegmentsGenesToSwap = 0
    var oddSegmentsGenesToSwap = 0
    crossIndices.forEachIndexed { index, crossIndex ->
        val count = if (index == 0) crossIndex + 1 else crossIndex - crossIndices[index - 1]
        if (index % 2 == 0) {
            evenSegmentsGenesToSwap += count
        } else {
            oddSegmentsGenesToSwap += count
        }
    }

    if (crossIndices.size % 2 == 0) {
        evenSegmentsGenesToSwap += size - 1 - crossIndices.last()
    } else {
        oddSegmentsGenesToSwap += size - 1 - crossIndices.last()
    }

    var endCross = if (evenSegmentsGenesToSwap <= oddSegmentsGenesToSwap) 0 else 1
    while (endCross <= crossIndices.size) {
        val end = if (endCross != crossIndices.size) crossIndices[endCross] else size - 1
        var start = if (endCross - 1 < 0) -1 else crossIndices[endCross - 1]
        while (start < end) swapper(++start)
        endCross += 2
    }
}

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun <T> geneSwap(first: Array<T>, second: Array<T>, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: BooleanArray, second: BooleanArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: ByteArray, second: ByteArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: CharArray, second: CharArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: DoubleArray, second: DoubleArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: FloatArray, second: FloatArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: IntArray, second: IntArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: LongArray, second: LongArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun <T> geneSwap(first: MutableList<T>, second: MutableList<T>, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [multiSwapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndices selected K-points indices of K-point crossover
 */
internal fun geneSwap(first: ShortArray, second: ShortArray, crossIndices: IntArray) =
    multiSwapStrategy(first.size, crossIndices) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }
