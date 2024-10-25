package kgal.operators.crossover

import kgal.chromosome.Chromosome
import kotlin.math.pow
import kotlin.random.Random

/**
 * Executes a simulated binary crossover that modify in-place the input chromosomes.
 * The simulated binary crossover expects [Chromosome.value] of floating point numbers.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param eta Crowding degree of the crossover. A high eta will produce children resembling to their parents,
 * while a small eta will produce solutions much more different.
 */
public fun crossoverSimulatedBinary(
    value1: DoubleArray,
    value2: DoubleArray,
    eta: Double,
    random: Random,
) {
    for (i in value1.indices) {
        val rand = random.nextDouble()
        var beta = if (rand <= 0.5) {
            2.0 * rand
        } else {
            1.0 / (2.0 * (1.0 - rand))
        }
        beta = beta.pow(1.0 / (eta + 1.0))

        value1[i] = 0.5 * (((1 + beta) * value1[i]) + ((1 - beta) * value2[i]))
        value2[i] = 0.5 * (((1 - beta) * value1[i]) + ((1 + beta) * value2[i]))
    }
}
