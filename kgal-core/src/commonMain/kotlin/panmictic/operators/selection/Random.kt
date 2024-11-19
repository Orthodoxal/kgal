package kgal.panmictic.operators.selection

import kgal.Population
import kgal.operators.selection.selectionRandom
import kgal.panmictic.PanmicticLifecycle
import kgal.processor.parallelism.ParallelismConfig

/**
 * Executes random selection step for [Population]:
 *
 * Selection absolutely random!
 *
 * @param parallelismLimit limit of parallel workers
 */
public suspend fun <V, F> PanmicticLifecycle<V, F>.selRandom(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
): Unit = selection(parallelismLimit) { source, random -> selectionRandom(source, random) }
