package kgal.operators.mutation

import kgal.chromosome.Chromosome
import kgal.utils.nextGaussian
import kgal.utils.randomByChance
import kotlin.random.Random

/**
 * Applies a gaussian mutation of mean [mean] and standard deviation [stddev] on the input chromosome.
 * This mutation expects [Chromosome.value] of floating point numbers.
 * @param value chromosome to be mutated
 * @param mean average.
 * Is the mathematical expectation (mean value) of the normal distribution from which the random value will be returned.
 * It defines the center of the distribution.
 * @param stddev standard deviation.
 * It is a measure of the spread or variability of values around the mean.
 * The larger the value of the standard deviation, the wider the distribution,
 * and therefore the values will be more spread out around the mean.
 * @param chance the probability of each attribute to be mutated
 */
public fun mutationGaussian(
    value: DoubleArray,
    mean: Double,
    stddev: Double,
    chance: Double,
    random: Random,
) {
    value.indices.forEach { i ->
        randomByChance(chance, random) { value[i] = random.nextGaussian(mean, stddev) }
    }
}
