package kgal.panmictic.operators.selection

import kgal.Population
import kgal.operators.selection.selectionBest
import kgal.operators.selection.selectionWorst
import kgal.panmictic.PanmicticLifecycle
import kgal.size
import kgal.utils.fillWithSameFromSource

/**
 * Executes best selection step for [Population].
 * @param count number of selected best values
 * @param fillWithSelected if true (default) fills all population with selected clones
 */
public fun <V, F> PanmicticLifecycle<V, F>.selBest(
    count: Int,
    fillWithSelected: Boolean = true,
) {
    population.get()
        .selectionBest(count, from = 0, to = size)
        .takeIf { fillWithSelected }
        ?.fillWithSameFromSource(from = count, to = size)
}


/**
 * Executes worst selection step for [Population].
 * @param count number of selected worst values
 * @param fillWithSelected if true (default) fills all population with selected clones
 */
public fun <V, F> PanmicticLifecycle<V, F>.selWorst(
    count: Int,
    fillWithSelected: Boolean = true,
) {
    population.get()
        .selectionWorst(count, from = 0, to = size)
        .takeIf { fillWithSelected }
        ?.fillWithSameFromSource(from = count, to = size)
}
