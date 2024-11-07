package kgal.cellular.operators.crossover

import kgal.cellular.CellLifecycle
import kgal.operators.crossover.crossoverOnePoint
import kotlin.jvm.JvmName

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointArray")
public fun <T, F> CellLifecycle<Array<T>, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointBooleanArray")
public fun <F> CellLifecycle<BooleanArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointByteArray")
public fun <F> CellLifecycle<ByteArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointCharArray")
public fun <F> CellLifecycle<CharArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointDoubleArray")
public fun <F> CellLifecycle<DoubleArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointFloatArray")
public fun <F> CellLifecycle<FloatArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointIntArray")
public fun <F> CellLifecycle<IntArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointLongArray")
public fun <F> CellLifecycle<LongArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointMutableList")
public fun <T, F> CellLifecycle<MutableList<T>, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOnePointShortArray")
public fun <F> CellLifecycle<ShortArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}
