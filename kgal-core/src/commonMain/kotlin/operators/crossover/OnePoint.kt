package kgal.operators.crossover

import kotlin.random.Random

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun <T> crossoverOnePoint(value1: Array<T>, value2: Array<T>, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: BooleanArray, value2: BooleanArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: ByteArray, value2: ByteArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: CharArray, value2: CharArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: DoubleArray, value2: DoubleArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: FloatArray, value2: FloatArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: IntArray, value2: IntArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: LongArray, value2: LongArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun <T> crossoverOnePoint(value1: MutableList<T>, value2: MutableList<T>, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes a one point crossover on the input sequence individuals. The two values are modified in place.
 *
 * Example:
 * ```
 * Parent 1: ++++++++
 * Parent 2: --------
 * Generated cross index: 4
 * Child 1: +++++---
 * Child 2: -----+++
 * ```
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOnePoint(value1: ShortArray, value2: ShortArray, random: Random): Unit =
    geneSwap(value1, value2, random.nextInt(1, value1.lastIndex))

/**
 * Executes swap strategy (choose indices to swap) for one point crossover using [swapper]
 * @param size size of value
 * @param crossIndex selected index of one point crossover
 * @param swapper action which swap values by index of parents chromosomes
 */
internal inline fun swapStrategy(size: Int, crossIndex: Int, swapper: (swapIndex: Int) -> Unit) {
    val isSecondHalf = crossIndex > size / 2
    var start = if (isSecondHalf) crossIndex else 0
    val end = if (isSecondHalf) size else crossIndex
    while (start != end) {
        swapper(start)
        start++
    }
}

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun <T> geneSwap(first: Array<T>, second: Array<T>, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: BooleanArray, second: BooleanArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: ByteArray, second: ByteArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: CharArray, second: CharArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: DoubleArray, second: DoubleArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: FloatArray, second: FloatArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: IntArray, second: IntArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: LongArray, second: LongArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun <T> geneSwap(first: MutableList<T>, second: MutableList<T>, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }

/**
 * Executes gene swap by [swapStrategy]
 * @param first first parent value
 * @param second second parent value
 * @param crossIndex selected index of one point crossover
 */
internal fun geneSwap(first: ShortArray, second: ShortArray, crossIndex: Int) =
    swapStrategy(first.size, crossIndex) { swapIndex ->
        val temp = first[swapIndex]
        first[swapIndex] = second[swapIndex]
        second[swapIndex] = temp
    }
