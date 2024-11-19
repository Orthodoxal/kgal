package kgal.cellular.operators.mutation

import kgal.cellular.CellEvolveScope
import kgal.cellular.CellularGA
import kgal.chromosome.Chromosome
import kgal.utils.randomByChance

/**
 * Performs a mutation step for population in [CellularGA] that modify in-place the input chromosomes.
 * Mutates [CellEvolveScope.cell].
 *
 * Welcome to use for your own implementations! It is a base function for executing [mutation] step in [CellularGA].
 * @param chance chance of mutation for each chromosome
 * @param mutation specific mutation action (How chromosomes will be mutated)
 */
public inline fun <V, F> CellEvolveScope<V, F>.mutation(
    chance: Double,
    mutation: (chromosome: Chromosome<V, F>) -> Unit,
) {
    randomByChance(chance, random) { mutation(cell) }
    randomByChance(chance, random) { mutation(neighbors[0]) }
}
