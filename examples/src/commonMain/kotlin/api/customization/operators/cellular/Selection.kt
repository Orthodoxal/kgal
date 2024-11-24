package api.customization.operators.cellular

import kgal.cellular.CellEvolveScope
import kgal.cellular.CellularEvolveScope
import kgal.cellular.CellularGA
import kgal.cellular.operators.selection.selection

/**
 * Example for creating custom selection operator for [CellularGA].
 *
 * Selection stage is to select neighbor(s) that will have the right to create offspring with target chromosome `cell`.
 * 1) create operator in [CellEvolveScope] (use [CellularEvolveScope] for deep)
 * 2) use base [selection] operator, it's get source
 * 3) describe how to choose neighbor chromosome from `source` inside lambda of [selection] operator
 */
private fun <V, F> CellEvolveScope<V, F>.customSelection() {
    selection { source ->
        source.random(random) // chromosome will be selected randomly
    }
}
