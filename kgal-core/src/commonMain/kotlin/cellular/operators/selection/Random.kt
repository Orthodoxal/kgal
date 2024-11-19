package kgal.cellular.operators.selection

import kgal.cellular.CellEvolveScope
import kgal.operators.selection.selectionRandom

/**
 * Executes random selection step for [CellEvolveScope.neighbors].
 *
 * Selection absolutely random!
 */
public fun <V, F> CellEvolveScope<V, F>.selRandom(): Unit =
    selection { source -> selectionRandom(source, random) }
