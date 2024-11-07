package kgal.cellular.operators.selection

import kgal.cellular.CellLifecycle
import kgal.cellular.CellularGA
import kgal.cellular.CellularNeighborhood
import kgal.chromosome.Chromosome

/**
 * Performs a selection step for population in [CellularGA].
 * Selects one partner from neighbors determined by the [CellularNeighborhood] with [selection] function.
 * Selected partner will be moved to the first position of [CellLifecycle.neighbors] (index = 0).
 *
 * Welcome to use for your own implementations! It is a base function for executing [selection] step in [CellularGA].
 * @param selection specific selection action (How chromosomes will be selected from current population)
 */
public inline fun <V, F> CellLifecycle<V, F>.selection(
    selection: (source: Array<Chromosome<V, F>>) -> Chromosome<V, F>,
) {
    val chromosome = selection(neighbors)
    neighbors[0] = chromosome
}
