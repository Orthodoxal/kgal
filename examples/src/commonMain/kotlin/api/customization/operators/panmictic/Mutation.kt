package api.customization.operators.panmictic

import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.PanmicticGA
import kgal.panmictic.operators.mutation.mutation

/**
 * Example for creating custom mutation operator for [PanmicticGA].
 *
 * Mutation stage is to randomly (with low probability) changed offspring of the current population (usually after crossover stage).
 * 1) create operator in [PanmicticEvolveScope]
 * 2) provide [chance] (probability of mutation per chromosome), [parallelismLimit] (optional)
 * 3) use base [mutation] operator, it's implement parallelism, elitism and chance
 * 4) describe how chromosomes will be changed inside lambda of [mutation] operator, use safe random argument, `NOTE` chromosomes modified in place!
 */
private suspend fun <V, F> PanmicticEvolveScope<V, F>.customMutation(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
) {
    mutation(chance, parallelismLimit) { chromosome, random ->
        chromosome.value = population[0].value // chromosome randomly gets genes from first in population
    }
}
