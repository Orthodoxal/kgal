package kgal.cellular.operators.mutation

import kgal.cellular.CellLifecycle
import kgal.chromosome.Chromosome
import kgal.operators.mutation.mutationGaussian

/**
 * Applies a gaussian mutation of mean [mean] and standard deviation [stddev] on the input chromosome.
 * This mutation expects [Chromosome.value] of floating point numbers.
 * @param mean average.
 * Is the mathematical expectation (mean value) of the normal distribution from which the random value will be returned.
 * It defines the center of the distribution.
 * @param stddev standard deviation.
 * It is a measure of the spread or variability of values around the mean.
 * The larger the value of the standard deviation, the wider the distribution,
 * and therefore the values will be more spread out around the mean.
 * @param chance chance of mutation between a pair of chromosomes
 * @param gaussianChance the probability of each attribute to be mutated
 */
public fun <F> CellLifecycle<DoubleArray, F>.mutGaussian(
    mean: Double,
    stddev: Double,
    chance: Double,
    gaussianChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationGaussian(chromosome.value, mean, stddev, gaussianChance, random)
}
