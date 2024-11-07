package kgal.cellular.operators.selection

import kgal.cellular.CellLifecycle
import kgal.chromosome.Chromosome
import kgal.operators.selection.selectionTournament

/**
 * Executes tournament selection step for [CellLifecycle.neighbors]:
 *
 * Randomly select [size] chromosome for tournament between [CellLifecycle.neighbors].
 * After that the best [Chromosome] is selected
 * and moved to first position of [CellLifecycle.neighbors] (with index = 0).
 * @param size the size of tournament (number selected chromosomes)
 */
public fun <V, F> CellLifecycle<V, F>.selTournament(
    size: Int,
): Unit = selection { source -> selectionTournament(source, size, random) }
