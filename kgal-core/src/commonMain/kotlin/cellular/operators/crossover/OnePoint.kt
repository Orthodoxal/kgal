package kgal.cellular.operators.crossover

import kgal.cellular.CellEvolveScope
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
public fun <T, F> CellEvolveScope<Array<T>, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<BooleanArray, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<ByteArray, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<CharArray, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<DoubleArray, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<FloatArray, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<IntArray, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<LongArray, F>.cxOnePoint(
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
public fun <T, F> CellEvolveScope<MutableList<T>, F>.cxOnePoint(
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
public fun <F> CellEvolveScope<ShortArray, F>.cxOnePoint(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}
