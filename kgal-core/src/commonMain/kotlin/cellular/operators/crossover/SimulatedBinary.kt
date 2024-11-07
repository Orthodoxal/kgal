package kgal.cellular.operators.crossover

import kgal.cellular.CellLifecycle
import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverSimulatedBinary
import kotlin.jvm.JvmName

/**
 * Executes a simulated binary crossover that modify in-place the input chromosomes.
 * The simulated binary crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param eta Crowding degree of the crossover. A high eta will produce children resembling to their parents,
 * while a small eta will produce solutions much more different.
 */
@JvmName("cxSimulatedBinaryDoubleArray")
public fun <F> CellLifecycle<DoubleArray, F>.cxSimulatedBinary(
    chance: Double,
    eta: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverSimulatedBinary(chromosome1.value, chromosome2.value, eta, random)
}
