package api.customization.operators.cellular

import kgal.cellular.CellEvolveScope
import kgal.cellular.CellularEvolveScope
import kgal.cellular.CellularGA
import kgal.cellular.operators.crossover.crossover

/**
 * Example for creating custom crossover operator for [CellularGA].
 *
 * Crossover stage is to create offspring from current target chromosome `cell` and neighborhood (usually after selection stage).
 * 1) create operator in [CellEvolveScope] (use [CellularEvolveScope] for deep)
 * 2) provide [chance] (probability of crossing)
 * 3) use base [crossover] operator, it's implement chance and get target chromosomes
 * 4) describe how chromosome will be changed inside lambda of [crossover] operator, `NOTE` chromosomes modified in place!
 */
private fun <V, F> CellEvolveScope<V, F>.customCrossover(
    chance: Double,
) {
    crossover(chance) { chromosome1, chromosome2 ->
        // modify in place
        val swapper = chromosome1.value
        chromosome1.value = chromosome2.value
        chromosome2.value = swapper
    }
}
