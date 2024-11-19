package kgal.cellular.operators.crossover

import kgal.cellular.CellEvolveScope
import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverSimulatedBinaryBounded
import kotlin.jvm.JvmName

/**
 * Executes a simulated binary crossover that modify in-place the input chromosomes.
 * The simulated binary crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param eta Crowding degree of the crossover. A high eta will produce children resembling to their parents,
 * while a small eta will produce solutions much more different.
 * @param low a value that is the lower bound of the search space.
 * @param up a value that is the upper bound of the search space.
 */
@JvmName("cxSimulatedBinaryBoundedDoubleArray")
public fun <F> CellEvolveScope<DoubleArray, F>.cxSimulatedBinaryBounded(
    chance: Double,
    eta: Double,
    low: Double,
    up: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverSimulatedBinaryBounded(chromosome1.value, chromosome2.value, eta, low, up, random)
}
