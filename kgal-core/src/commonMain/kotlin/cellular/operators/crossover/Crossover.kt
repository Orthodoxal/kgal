package kgal.cellular.operators.crossover

import kgal.cellular.CellEvolveScope
import kgal.cellular.CellularGA
import kgal.cellular.operators.selection.selection
import kgal.chromosome.Chromosome
import kgal.utils.randomByChance

/**
 * Performs a crossover step for population in [CellularGA] that modify in-place the input chromosomes.
 * - [selection] stage is expected before the call.
 * - executes [crossover] between [CellEvolveScope.cell] and neighbor [CellEvolveScope.neighbors] from index = 0
 * (neighbor is previously selected)
 *
 * Welcome to use for your own implementations! It is a base function for executing [crossover] step in [CellularGA].
 * @param chance chance of crossover between a pair of chromosomes
 * @param crossover specific crossing action (How chromosomes will be crossed)
 * @see selection
 */
public inline fun <V, F> CellEvolveScope<V, F>.crossover(
    chance: Double,
    crossover: (chromosome1: Chromosome<V, F>, chromosome2: Chromosome<V, F>) -> Unit,
): Unit = randomByChance(chance, random) {
    val parent1 = cell
    val parent2 = neighbors[0]
    if (parent1 != parent2) crossover(parent1, parent2)
}
