package kgal.panmictic.operators.crossover

import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverBlend
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
import kotlin.jvm.JvmName

/**
 * Executes a blend crossover that modify in-place the input chromosomes.
 * The blend crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param alpha Extent of the interval in which the new values can be drawn for each attribute on both side of the parents’ attributes.
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxBlendDoubleArray")
public suspend fun <F> PanmicticLifecycle<DoubleArray, F>.cxBlend(
    chance: Double,
    alpha: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverBlend(chromosome1.value, chromosome2.value, alpha, random)
}

/**
 * Executes a blend crossover that modify in-place the input chromosomes.
 * The blend crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param alpha Extent of the interval in which the new values can be drawn for each attribute on both side of the parents’ attributes.
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxBlendFloatArray")
public suspend fun <F> PanmicticLifecycle<FloatArray, F>.cxBlend(
    chance: Double,
    alpha: Float,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverBlend(chromosome1.value, chromosome2.value, alpha, random)
}
