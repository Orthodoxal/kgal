package kgal.panmictic.operators.crossover

import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverOrdered
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
import kotlin.jvm.JvmName

/**
 * Executes an ordered crossover (OX) on the input chromosome values. The two value are modified in place.
 * This crossover expects [Chromosome.value] as IntArray.
 * Mixes indexes without conflicts, preserving parental relationships.
 * @param chance chance of crossover between a pair of chromosomes
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxOrderedIntArray")
public suspend fun <F> PanmicticLifecycle<IntArray, F>.cxOrdered(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverOrdered(chromosome1.value, chromosome2.value, random)
}
