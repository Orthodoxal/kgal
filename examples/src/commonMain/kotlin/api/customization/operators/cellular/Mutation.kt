package api.customization.operators.cellular

import kgal.cellular.CellEvolveScope
import kgal.cellular.CellularEvolveScope
import kgal.cellular.CellularGA
import kgal.cellular.operators.mutation.mutation

/**
 * Example for creating custom mutation operator for [CellularGA].
 *
 * Mutation stage is to randomly (with low probability) changed child chromosome (usually after crossover stage).
 * 1) create operator in [CellEvolveScope] (use [CellularEvolveScope] for deep)
 * 2) provide [chance] (probability of mutation child chromosome)
 * 3) use base [mutation] operator, it's implement parallelism, elitism and chance
 * 4) describe how chromosome will be changed inside lambda of [mutation] operator, `NOTE` chromosomes modified in place!
 */
private fun <V, F> CellEvolveScope<V, F>.customMutation(
    chance: Double,
) {
    mutation(chance) { chromosome ->
        chromosome.value = neighbors.random(random).value // chromosome randomly gets genes from random neighbor
    }
}
