package kgal.operators.crossover

import kgal.chromosome.Chromosome
import kotlin.math.abs
import kotlin.math.pow
import kotlin.random.Random

/**
 * Executes a simulated binary crossover that modify in-place the input chromosomes.
 * The simulated binary crossover expects [Chromosome.value] of floating point numbers.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param eta Crowding degree of the crossover. A high eta will produce children resembling to their parents,
 * while a small eta will produce solutions much more different.
 * @param low a value that is the lower bound of the search space.
 * @param up a value that is the upper bound of the search space.
 */
public fun crossoverSimulatedBinaryBounded(
    value1: DoubleArray,
    value2: DoubleArray,
    eta: Double,
    low: Double,
    up: Double,
    random: Random,
) {
    val size = minOf(value1.size, value2.size)

    for (i in 0..<size) {
        if (abs(value1[i] - value2[i]) < 1E14) continue

        val x1 = minOf(value1[i], value2[i])
        val x2 = maxOf(value1[i], value2[i])
        val rand = random.nextDouble()

        var beta = 1.0 + (2.0 * (x1 - low) / (x2 - x1))
        var alpha = 2.0 - beta.pow(-(eta + 1.0))
        var betaQ = betaQ(rand, alpha, eta)
        var c1 = 0.5 * (x1 + x2 - betaQ * (x2 - x1))

        beta = 1.0 + (2.0 * (up - x2) / (x2 - x1))
        alpha = 2.0 - beta.pow(-(eta + 1.0))
        betaQ = betaQ(rand, alpha, eta)
        var c2 = 0.5 * (x1 + x2 + betaQ * (x2 - x1))

        c1 = c1.coerceIn(low, up)
        c2 = c2.coerceIn(low, up)

        if (random.nextDouble() <= 0.5) {
            value1[i] = c2
            value2[i] = c1
        } else {
            value1[i] = c1
            value2[i] = c2
        }
    }
}

private fun betaQ(rand: Double, alpha: Double, eta: Double) =
    if (rand <= 1.0 / alpha) {
        (rand * alpha).pow(1.0 / (eta + 1))
    } else {
        (1.0 / (2.0 - rand * alpha)).pow(1.0 / (eta + 1))
    }
