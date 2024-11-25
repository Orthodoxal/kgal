package api.customization.operators.panmictic

import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.PanmicticGA
import kgal.panmictic.operators.crossover.CrossoverType
import kgal.panmictic.operators.crossover.crossover

/**
 * Example for creating custom crossover operator for [PanmicticGA].
 *
 * Crossover stage is to create offspring from current population (usually after selection stage).
 * 1) create operator in [PanmicticEvolveScope]
 * 2) provide [chance] (probability of crossing), [parallelismLimit] (optional), [crossoverType] (optional)
 * 3) use base [crossover] operator, it's implement parallelism, elitism, chance and crossoverType
 * 4) describe how chromosomes will be changed inside lambda of [crossover] operator,
 * use safe random argument, `NOTE` chromosomes modified in place!
 */
private suspend fun <V, F> PanmicticEvolveScope<V, F>.customCrossover(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
) {
    crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
        // modify in place
        val swapper = chromosome1.value
        chromosome1.value = chromosome2.value
        chromosome2.value = swapper
    }
}
