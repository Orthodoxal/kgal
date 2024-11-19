package kgal.panmictic.operators.crossover

import kgal.operators.crossover.crossoverUniform
import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.PanmicticGA
import kotlin.jvm.JvmName

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformArray")
public suspend fun <T, F> PanmicticEvolveScope<Array<T>, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformBooleanArray")
public suspend fun <F> PanmicticEvolveScope<BooleanArray, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformByteArray")
public suspend fun <F> PanmicticEvolveScope<ByteArray, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformCharArray")
public suspend fun <F> PanmicticEvolveScope<CharArray, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformDoubleArray")
public suspend fun <F> PanmicticEvolveScope<DoubleArray, F>.cxUniform(
    chance: Double = 0.9,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformFloatArray")
public suspend fun <F> PanmicticEvolveScope<FloatArray, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformIntArray")
public suspend fun <F> PanmicticEvolveScope<IntArray, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformLongArray")
public suspend fun <F> PanmicticEvolveScope<LongArray, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformMutableList")
public suspend fun <T, F> PanmicticEvolveScope<MutableList<T>, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxUniformShortArray")
public suspend fun <F> PanmicticEvolveScope<ShortArray, F>.cxUniform(
    chance: Double,
    chanceUniform: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}
