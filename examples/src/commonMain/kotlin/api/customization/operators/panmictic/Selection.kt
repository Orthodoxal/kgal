package api.customization.operators.panmictic

import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.PanmicticGA
import kgal.panmictic.operators.selection.selection
import kgal.processor.parallelism.ParallelismConfig

/**
 * Example for creating custom selection operator for [PanmicticGA].
 *
 * Selection stage is to fill the population only those chromosomes that will have the right to create offspring.
 * 1) create operator in [PanmicticEvolveScope]
 * 2) provide [parallelismLimit] (optional)
 * 3) use base [selection] operator, it's implement parallelism and elitism
 * 4) describe how to choose chromosome from `source` inside lambda of [selection] operator, use safe random argument
 */
private suspend fun <V, F> PanmicticEvolveScope<V, F>.customSelection(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    selection(parallelismLimit) { source, random ->
        source.random(random) // chromosome will be selected randomly
    }
}
