package kgal.cellular.operators.crossover

import kgal.cellular.CellEvolveScope
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
public fun <T, F> CellEvolveScope<Array<T>, F>.cxKPoint(
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
public fun <F> CellEvolveScope<BooleanArray, F>.cxKPoint(
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
public fun <F> CellEvolveScope<ByteArray, F>.cxKPoint(
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
public fun <F> CellEvolveScope<CharArray, F>.cxKPoint(
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
public fun <F> CellEvolveScope<DoubleArray, F>.cxKPoint(
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
public fun <F> CellEvolveScope<FloatArray, F>.cxKPoint(
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
public fun <F> CellEvolveScope<IntArray, F>.cxKPoint(
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
public fun <F> CellEvolveScope<LongArray, F>.cxKPoint(
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
public fun <T, F> CellEvolveScope<MutableList<T>, F>.cxKPoint(
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
public fun <F> CellEvolveScope<ShortArray, F>.cxKPoint(
    count: Int,
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}
