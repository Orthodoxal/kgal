package kgal.panmictic.operators.crossover

import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverSimulatedBinaryBounded
import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.PanmicticGA
import kotlin.jvm.JvmName

/**
 * Executes a simulated binary crossover that modify in-place the input chromosomes.
 * The simulated binary crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param eta Crowding degree of the crossover. A high eta will produce children resembling to their parents,
 * while a small eta will produce solutions much more different.
 * @param low a value that is the lower bound of the search space.
 * @param up a value that is the upper bound of the search space.
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 */
@JvmName("cxSimulatedBinaryBoundedDoubleArray")
public suspend fun <F> PanmicticEvolveScope<DoubleArray, F>.cxSimulatedBinaryBounded(
    chance: Double,
    eta: Double,
    low: Double,
    up: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
): Unit = crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
    crossoverSimulatedBinaryBounded(chromosome1.value, chromosome2.value, eta, low, up, random)
}
