package kgal.operators.crossover

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Executes a blend crossover that modify in-place the input chromosomes.
 * The blend crossover expects [Chromosome.value] of floating point numbers.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param alpha Extent of the interval in which the new values can be drawn for each attribute on both side of the parents’ attributes.
 */
public fun crossoverBlend(
    value1: DoubleArray,
    value2: DoubleArray,
    alpha: Double,
    random: Random,
) {
    repeat(value1.size) {
        val gamma = (1.0 + 2.0 * alpha) * random.nextDouble() - alpha
        value1[it] = (1.0 - gamma) * value1[it] + gamma * value2[it]
        value2[it] = gamma * value1[it] + (1.0 - gamma) * value2[it]
    }
}

/**
 * Executes a blend crossover that modify in-place the input individuals.
 * The blend crossover expects [Chromosome.value] of floating point numbers.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param alpha Extent of the interval in which the new values can be drawn for each attribute on both side of the parents’ attributes.
 */
public fun crossoverBlend(
    value1: FloatArray,
    value2: FloatArray,
    alpha: Float,
    random: Random,
) {
    repeat(value1.size) {
        val gamma = (1f + 2f * alpha) * random.nextFloat() - alpha
        value1[it] = (1f - gamma) * value1[it] + gamma * value2[it]
        value2[it] = gamma * value1[it] + (1f - gamma) * value2[it]
    }
}
