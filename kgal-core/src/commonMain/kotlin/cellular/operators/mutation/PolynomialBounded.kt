package kgal.cellular.operators.mutation

import kgal.cellular.CellEvolveScope
import kgal.chromosome.Chromosome
import kgal.operators.mutation.mutationPolynomialBounded

/**
 * Polynomial mutation as implemented in original NSGA-II algorithm in C by Deb.
 * This mutation expects [Chromosome.value] of floating point numbers.
 * @param eta crowding degree of the mutation.
 * A high eta will produce a mutant resembling its parent, while a small eta will produce a solution much more different.
 * @param low A value that is the lower bound of the search space.
 * @param up A value that is the upper bound of the search space.
 * @param chance chance of mutation between a pair of chromosomes
 * @param polynomialBoundedChance the probability of each attribute to be mutated
 */
public fun <F> CellEvolveScope<DoubleArray, F>.mutPolynomialBounded(
    eta: Double,
    low: Double,
    up: Double,
    chance: Double,
    polynomialBoundedChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationPolynomialBounded(chromosome.value, eta, low, up, polynomialBoundedChance, random)
}
