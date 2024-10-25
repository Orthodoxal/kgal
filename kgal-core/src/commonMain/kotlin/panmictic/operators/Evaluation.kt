package kgal.panmictic.operators

import kgal.chromosome.Chromosome
import kgal.operators.evaluate
import kgal.operators.evaluateAll
import kgal.panmictic.PanmicticConfig
import kgal.panmictic.PanmicticLifecycle
import kgal.panmictic.PanmicticPopulation
import kgal.size
import kgal.utils.findOrderStatistic

/**
 * Performs a fitness calculation step for chromosomes in the population.
 *
 * If [PanmicticConfig.elitism] > 0 moves elite chromosomes forward:
 * - it is guaranteed that the first [PanmicticConfig.elitism] individuals will be the best in the population after evaluation
 * - it is guaranteed that the first [PanmicticConfig.elitism] individuals will be the best in the population
 * in descending order after evaluation only if [sortAfter] is true
 *
 * NOTE the [evaluateElite] flag - elite chromosomes are not calculated by default!
 *
 * See [evaluate] for calculating the fitness of a single chromosome.
 * @param parallelismLimit limit of parallel workers
 * @param evaluateElite evaluate fitness function for elite chromosomes.
 * Default is false for the purpose of optimizing calculations
 * because basically it is need only at [PanmicticConfig.beforeEvolution] stage
 * when the [PanmicticPopulation] fitness values have probably not yet been calculated.
 * In other cases, it is considered that the elite chromosomes were not subject to change,
 * and therefore did not change their fitness values.
 *
 * !!!   NOTE   !!!
 *
 * If you are planning to increase [PanmicticLifecycle.elitism] after GA started or
 * your [PanmicticLifecycle.fitnessFunction] does not guarantee the same result
 * for the same [Chromosome.value] throughout the entire GA run (it has side effects)
 * Change [evaluateElite] to true for correct behavior
 * @param sortAfter if true population will be sorting in descending order after evaluation
 * @param fitnessFunction fitnessFunction for evaluation stage
 */
public suspend fun <V, F> PanmicticLifecycle<V, F>.evaluation(
    parallelismLimit: Int = parallelismConfig.workersCount,
    evaluateElite: Boolean = false,
    sortAfter: Boolean = false,
    fitnessFunction: (V) -> F = this.fitnessFunction,
) {
    evaluateAll(
        start = if (evaluateElite) 0 else elitism,
        end = size,
        parallelismLimit = parallelismLimit,
        fitnessFunction = fitnessFunction,
    )

    when {
        sortAfter -> population.sort()
        elitism > 0 -> population.get().findOrderStatistic(k = elitism, from = 0, to = size, ascending = false)
    }
}
