package kgal.panmictic.operators.crossover

import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverSimulatedBinary
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
import kotlin.jvm.JvmName

/**
 * Executes a simulated binary crossover that modify in-place the input chromosomes.
 * The simulated binary crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param eta Crowding degree of the crossover. A high eta will produce children resembling to their parents,
 * while a small eta will produce solutions much more different.
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxSimulatedBinaryDoubleArray")
public suspend fun <F> PanmicticLifecycle<DoubleArray, F>.cxSimulatedBinary(
    chance: Double,
    eta: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverSimulatedBinary(chromosome1.value, chromosome2.value, eta, random)
}
