package kgal.panmictic.operators.evaluation

import kgal.operators.evaluate
import kgal.operators.fitnessAll
import kgal.panmictic.PanmicticLifecycle
import kgal.size

/**
 * Performs a fitness calculation step for all chromosomes in the population.
 *
 * See [evaluate] for calculating the fitness of a single chromosome.
 */
public suspend fun <V, F> PanmicticLifecycle<V, F>.evaluation(
    parallelismLimit: Int = parallelismConfig.workersCount,
    fitnessFunction: (V) -> F = this.fitnessFunction,
): Unit = fitnessAll(elitism, size, parallelismLimit, fitnessFunction)
