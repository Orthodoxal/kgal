package kgal.cellular.operators.selection

import kgal.cellular.CellLifecycle
import kgal.operators.selection.selectionRandom

/**
 * Executes random selection step for [CellLifecycle.neighbors].
 *
 * Selection absolutely random!
 */
public fun <V, F> CellLifecycle<V, F>.selRandom(): Unit =
    selection { source -> selectionRandom(source, random) }
