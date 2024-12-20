package kgal.panmictic.operators.crossover

import kgal.operators.crossover.crossoverOnePoint
import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.PanmicticGA
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointArray")
public suspend fun <T, F> PanmicticEvolveScope<Array<T>, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointBooleanArray")
public suspend fun <F> PanmicticEvolveScope<BooleanArray, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointByteArray")
public suspend fun <F> PanmicticEvolveScope<ByteArray, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointCharArray")
public suspend fun <F> PanmicticEvolveScope<CharArray, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointDoubleArray")
public suspend fun <F> PanmicticEvolveScope<DoubleArray, F>.cxOnePoint(
    chance: Double = 0.9,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointFloatArray")
public suspend fun <F> PanmicticEvolveScope<FloatArray, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointIntArray")
public suspend fun <F> PanmicticEvolveScope<IntArray, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointLongArray")
public suspend fun <F> PanmicticEvolveScope<LongArray, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointMutableList")
public suspend fun <T, F> PanmicticEvolveScope<MutableList<T>, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
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
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOnePointShortArray")
public suspend fun <F> PanmicticEvolveScope<ShortArray, F>.cxOnePoint(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverOnePoint(chromosome1.value, chromosome2.value, random)
}
