package kgal.cellular.operators.selection

import kgal.cellular.CellEvolveScope
import kgal.chromosome.Chromosome
import kgal.operators.selection.selectionTournament

/**
 * Executes tournament selection step for [CellEvolveScope.neighbors]:
 *
 * Randomly select [size] chromosome for tournament between [CellEvolveScope.neighbors].
 * After that the best [Chromosome] is selected
 * and moved to first position of [CellEvolveScope.neighbors] (with index = 0).
 * @param size the size of tournament (number selected chromosomes)
 */
public fun <V, F> CellEvolveScope<V, F>.selTournament(
    size: Int,
): Unit = selection { source -> selectionTournament(source, size, random) }
