package kgal.operators

import kgal.Lifecycle
import kgal.chromosome.Chromosome
import kgal.statistics.stats.bestFitness

/**
 * Name for [Lifecycle.store] to store target value on previous iteration for comparison
 */
public const val STEADY_GENERATIONS: String = "STEADY_GENERATIONS"

/**
 * Name for [Lifecycle.store] to store steady iteration
 */
public const val STEADY_GENERATIONS_CURRENT_ITERATION: String = "STEADY_GENERATIONS_CURRENT_ITERATION"

/**
 * Check for steady generation with [Chromosome.fitness].
 * @param targetIterationCount the maximum number of iterations during which the best fitness may not change
 * @return true if best fitness of Chromosome not changed after last [targetIterationCount] iterations.
 */
public inline fun <V, reified F, L : Lifecycle<V, F>> L.isSteadyGenerations(
    targetIterationCount: Int,
    storeNamePrevious: String = STEADY_GENERATIONS,
    storeNameIteration: String = STEADY_GENERATIONS_CURRENT_ITERATION,
): Boolean = onSteadyGenerations(
    target = bestFitness!!,
    targetIterationCount = targetIterationCount,
    onSteadyGenerations = { },
    equalsPredicate = { t, p -> t == p },
    storeNamePrevious = storeNamePrevious,
    storeNameIteration = storeNameIteration,
)

/**
 * Check for steady generation with [Chromosome.fitness].
 * @param targetIterationCount the maximum number of iterations during which the best fitness may not change
 * @param onSteadyGenerations action to invoke when it returns true
 * @return true if best fitness of Chromosome not changed after last [targetIterationCount] iterations.
 */
public inline fun <V, reified F, L : Lifecycle<V, F>> L.onSteadyGenerations(
    targetIterationCount: Int,
    storeNamePrevious: String = STEADY_GENERATIONS,
    storeNameIteration: String = STEADY_GENERATIONS_CURRENT_ITERATION,
    onSteadyGenerations: L.(target: F) -> Unit,
): Boolean = onSteadyGenerations(
    target = bestFitness!!,
    targetIterationCount = targetIterationCount,
    onSteadyGenerations = onSteadyGenerations,
    equalsPredicate = { t, p -> t == p },
    storeNamePrevious = storeNamePrevious,
    storeNameIteration = storeNameIteration,
)

/**
 * Check for steady generation with [target].
 * @param target some comparable value for comparison
 * @param targetIterationCount the maximum number of iterations during which the best fitness may not change
 * @param onSteadyGenerations action to invoke when it returns true
 * @return true if best fitness of Chromosome not changed after last [targetIterationCount] iterations.
 */
public inline fun <reified T : Comparable<T>, V, F, L : Lifecycle<V, F>> L.onSteadyGenerations(
    target: T,
    targetIterationCount: Int,
    storeNamePrevious: String = STEADY_GENERATIONS,
    storeNameIteration: String = STEADY_GENERATIONS_CURRENT_ITERATION,
    onSteadyGenerations: L.(target: T) -> Unit,
): Boolean = onSteadyGenerations(
    target = target,
    targetIterationCount = targetIterationCount,
    onSteadyGenerations = onSteadyGenerations,
    equalsPredicate = { t, p -> t == p },
    storeNamePrevious = storeNamePrevious,
    storeNameIteration = storeNameIteration,
)

/**
 * Check for steady generation with [target].
 * @param target some comparable value for comparison
 * @param targetIterationCount the maximum number of iterations during which the best fitness may not change
 * @param equalsPredicate action for comparison target with previous value
 * @param onSteadyGenerations action to invoke when it returns true
 * @return true if best fitness of Chromosome not changed after last [targetIterationCount] iterations.
 */
public inline fun <reified T, V, F, L : Lifecycle<V, F>> L.onSteadyGenerations(
    target: T,
    targetIterationCount: Int,
    storeNamePrevious: String = STEADY_GENERATIONS,
    storeNameIteration: String = STEADY_GENERATIONS_CURRENT_ITERATION,
    equalsPredicate: (current: T, previous: T) -> Boolean,
    onSteadyGenerations: L.(target: T) -> Unit,
): Boolean {
    val previous = store[storeNamePrevious] as? T
    val iteration = store[storeNameIteration] as? Int

    return when {
        previous == null || iteration == null -> {
            store[storeNamePrevious] = target
            store[storeNameIteration] = 1
            false
        }

        else -> {
            if (equalsPredicate(target, previous)) {
                if (iteration > targetIterationCount) {
                    store[storeNameIteration] = 0
                    onSteadyGenerations(target)
                    true
                } else {
                    store[storeNameIteration] = iteration + 1
                    false
                }
            } else {
                store[storeNamePrevious] = target
                store[storeNameIteration] = 1
                false
            }
        }
    }
}
