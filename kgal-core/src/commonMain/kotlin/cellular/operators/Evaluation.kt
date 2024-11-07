package kgal.cellular.operators

import kgal.cellular.CellLifecycle
import kgal.cellular.CellularLifecycle
import kgal.operators.evaluate
import kgal.operators.evaluateAll
import kgal.size

/**
 * Evaluation stage for [CellLifecycle.cell].
 * - NOTE! Use it only for [CellLifecycle]. For [CellularLifecycle] use [evaluationAll].
 * @param compareWithSecondChild if `true` calculates second child located in [CellLifecycle.neighbors] by index 0
 * and set it to [CellLifecycle.cell] if second child fitness is better than current [CellLifecycle.cell].
 * @param fitnessFunction fitnessFunction for evaluation stage
 */
public fun <V, F> CellLifecycle<V, F>.evaluation(
    compareWithSecondChild: Boolean = true,
    fitnessFunction: (V) -> F = this.fitnessFunction,
) {
    cell.evaluate(fitnessFunction)
    if (compareWithSecondChild) {
        val neighbor = neighbors[0]
        neighbor.evaluate(fitnessFunction)
        if (neighbor > cell) cell = neighbor
    }
}

/**
 * - NOTE! Use it only for [CellularLifecycle]. For [CellLifecycle] use [evaluation].
 * @param parallelismLimit limit of parallel workers
 * @param fitnessFunction fitnessFunction for evaluation stage
 */
public suspend fun <V, F> CellularLifecycle<V, F>.evaluationAll(
    parallelismLimit: Int = parallelismConfig.workersCount,
    fitnessFunction: (V) -> F = this.fitnessFunction,
): Unit = evaluateAll(0, size, parallelismLimit, fitnessFunction)
