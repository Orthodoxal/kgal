package kgal.panmictic.operators.selection

import kgal.Population
import kgal.operators.selection.selectionBest
import kgal.operators.selection.selectionWorst
import kgal.panmictic.PanmicticLifecycle
import kgal.utils.fillArrayChromosomeBySubArray

/**
 * Executes best selection step for [Population]:
 *
 * Fills all population with bests ones.
 *
 * @param count number of selected best values
 */
public fun <V, F> PanmicticLifecycle<V, F>.selBest(
    count: Int,
): Unit = fillArrayChromosomeBySubArray(population.get(), selectionBest(population.get(), count))


/**
 * Executes worst selection step for [Population]:
 *
 * Fills all population with worsts ones.
 *
 * @param count number of selected worst values
 */
public fun <V, F> PanmicticLifecycle<V, F>.selWorst(
    count: Int,
): Unit = fillArrayChromosomeBySubArray(population.get(), selectionWorst(population.get(), count))
