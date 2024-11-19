package kgal.panmictic.operators.selection

import kgal.Population
import kgal.chromosome.Chromosome
import kgal.factory
import kgal.operators.selection.selectionTournament
import kgal.panmictic.PanmicticLifecycle
import kgal.processor.parallelism.ParallelismConfig
import kgal.size

/**
 * Executes tournament selection step for [Population]:
 *
 * Randomly select [size] chromosome for tournament between them.
 * After that the best [Chromosome] is selected.
 * @param size the size of tournament (number selected chromosomes)
 * @param parallelismLimit limit of parallel workers
 */
public suspend fun <V, F> PanmicticLifecycle<V, F>.selTournament(
    size: Int,
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
): Unit = selection(parallelismLimit) { source, random -> selectionTournament(source, size, random) }

/**
 * Executes tournament selection step for [Population]:
 *
 * Randomly select [size] * [percent] chromosome for tournament between them.
 * After that the best [Chromosome] is selected.
 * The remaining free space ([size] * (1.0 - [percent])) will be filled with newly generated individuals by [factory]
 * @param percent determines the ratio of selected chromosomes to generated ones
 * @param size the size of tournament (number selected chromosomes)
 * @param parallelismLimit limit of parallel workers
 */
public suspend fun <V, F> PanmicticLifecycle<V, F>.selTournament(
    percent: Double,
    size: Int,
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    val limit = (this.size * percent).toInt()
    selectionWithIndex(parallelismLimit) { index, source, random ->
        if (index < limit) {
            selectionTournament(source, size, random)
        } else {
            random.factory()
        }
    }
}
