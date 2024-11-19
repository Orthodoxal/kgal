package kgal.cellular.operators.selection

import kgal.cellular.CellEvolveScope
import kgal.operators.selection.selectionBest
import kgal.operators.selection.selectionWorst
import kgal.utils.fillWithSameFromSource

/**
 * Executes best selection step for [CellEvolveScope.neighbors].
 * @param count number of selected best values
 * @param fillWithSelected if true fills all population with selected clones
 */
public fun <V, F> CellEvolveScope<V, F>.selBest(
    count: Int,
    fillWithSelected: Boolean = false,
) {
    neighbors
        .selectionBest(count, from = 0, to = neighbors.size)
        .takeIf { fillWithSelected }
        ?.fillWithSameFromSource(from = count, to = neighbors.size)
}

/**
 * Executes best selection step for [CellEvolveScope.neighbors].
 * @param count number of selected best values
 * @param fillWithSelected if true fills all population with selected clones
 */
public fun <V, F> CellEvolveScope<V, F>.selWorst(
    count: Int,
    fillWithSelected: Boolean = false,
) {
    neighbors
        .selectionWorst(count, from = 0, to = neighbors.size)
        .takeIf { fillWithSelected }
        ?.fillWithSameFromSource(from = count, to = neighbors.size)
}
