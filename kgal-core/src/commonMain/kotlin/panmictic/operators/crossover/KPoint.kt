package kgal.panmictic.operators.crossover

import kgal.operators.crossover.crossoverKPoint
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointArray")
public suspend fun <T, F> PanmicticLifecycle<Array<T>, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointBooleanArray")
public suspend fun <F> PanmicticLifecycle<BooleanArray, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointByteArray")
public suspend fun <F> PanmicticLifecycle<ByteArray, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointCharArray")
public suspend fun <F> PanmicticLifecycle<CharArray, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointDoubleArray")
public suspend fun <F> PanmicticLifecycle<DoubleArray, F>.cxKPoint(
    chance: Double = 0.9,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointFloatArray")
public suspend fun <F> PanmicticLifecycle<FloatArray, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointIntArray")
public suspend fun <F> PanmicticLifecycle<IntArray, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointLongArray")
public suspend fun <F> PanmicticLifecycle<LongArray, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointMutableList")
public suspend fun <T, F> PanmicticLifecycle<MutableList<T>, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxKPointShortArray")
public suspend fun <F> PanmicticLifecycle<ShortArray, F>.cxKPoint(
    chance: Double,
    count: Int,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverKPoint(chromosome1.value, chromosome2.value, count, random)
}
