package kgal.operators.selection

import kgal.chromosome.Chromosome
import kgal.utils.random
import kotlin.random.Random

/**
 * Executes tournament selection:
 *
 * Randomly select [tournamentSize] chromosome for tournament between them.
 * After that the best [Chromosome] is selected.
 * @param source the population of Chromosomes
 * @param tournamentSize the size of tournament (number selected chromosomes)
 */
public fun <V, F> selectionTournament(
    source: Array<Chromosome<V, F>>,
    tournamentSize: Int,
    random: Random,
): Chromosome<V, F> {
    val tournament = source.random(tournamentSize, random)
    return tournament.max().clone()
}
