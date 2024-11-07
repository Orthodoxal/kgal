package kgal.cellular.operators.crossover

import kgal.cellular.CellLifecycle
import kgal.operators.crossover.crossoverKPoint
import kotlin.jvm.JvmName

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointArray")
public fun <T, F> CellLifecycle<Array<T>, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointBooleanArray")
public fun <F> CellLifecycle<BooleanArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointByteArray")
public fun <F> CellLifecycle<ByteArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointCharArray")
public fun <F> CellLifecycle<CharArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointDoubleArray")
public fun <F> CellLifecycle<DoubleArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointFloatArray")
public fun <F> CellLifecycle<FloatArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointIntArray")
public fun <F> CellLifecycle<IntArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointLongArray")
public fun <F> CellLifecycle<LongArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointMutableList")
public fun <T, F> CellLifecycle<MutableList<T>, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}

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
 * @param chance chance of crossover between a pair of chromosomes
 * @param count k-points for crossover
 */
@JvmName("cxKPointShortArray")
public fun <F> CellLifecycle<ShortArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}
