package kgal.cellular.operators

import kgal.cellular.CellEvolveScope
import kgal.cellular.CellularEvolveScope
import kgal.operators.evaluate
import kgal.operators.evaluateAll
import kgal.size

/**
 * Evaluation stage for [CellEvolveScope.cell].
 * - NOTE! Use it only for [CellEvolveScope]. For [CellularEvolveScope] use [evaluationAll].
 * @param compareWithSecondChild if `true` calculates second child located in [CellEvolveScope.neighbors] by index 0
 * and set it to [CellEvolveScope.cell] if second child fitness is better than current [CellEvolveScope.cell].
 * @param fitnessFunction fitnessFunction for evaluation stage
 */
public fun <V, F> CellEvolveScope<V, F>.evaluation(
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
 * - NOTE! Use it only for [CellularEvolveScope]. For [CellEvolveScope] use [evaluation].
 * @param parallelismLimit limit of parallel workers
 * @param fitnessFunction fitnessFunction for evaluation stage
 */
public suspend fun <V, F> CellularEvolveScope<V, F>.evaluationAll(
    parallelismLimit: Int = parallelismConfig.workersCount,
    fitnessFunction: (V) -> F = this.fitnessFunction,
): Unit = evaluateAll(0, size, parallelismLimit, fitnessFunction)
