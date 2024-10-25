package kgal.panmictic.operators

import kgal.Population
import kgal.operators.evaluateAll
import kgal.panmictic.PanmicticLifecycle
import kgal.panmictic.PanmicticPopulation

/**
 * Sets [PanmicticPopulation.size] and [PanmicticPopulation.buffer] of population.
 *
 * Prefer [adjustSize] over [resize] to increase or decrease only size by step.
 *
 * Prefer [adjustBuffer] over [resize] to increase or decrease only buffer by step.
 *
 * NOTE!
 * - Changing only [newSize] ([newBuffer] is null) will automatically calculate the value of [PanmicticPopulation.buffer]:
 * ```
 * evolve {
 *     var size = population.size // = 100
 *     var buffer = population.buffer // = 50
 *     resize(newSize = size + 10)
 *     size = population.size // = 110
 *     buffer = population.buffer // = 40
 *     resize(newSize = size + 50)
 *     // buffer limit exceeded, created new array with maxSize = 160
 *     size = population.size // = 160
 *     buffer = population.buffer // = 0 (buffer overflow)
 *     resize(newSize = size - 60)
 *     size = population.size // = 100
 *     buffer = population.buffer // = 60 (buffer increased cause overflow)
 * }
 * ```
 *
 * @param newSize Set as new [PanmicticPopulation.size] value if not null.
 * - Always must be positive
 * - When decreasing the value, the array will not be copied.
 * The freed space will be marked as buffer - the value of [PanmicticPopulation.buffer] will be increased by oldSize - newSize
 * - When increasing the value AND new value less or equal to [PanmicticPopulation.maxSize] - the array will not be copied.
 * Space will be taken from buffer - the value of [PanmicticPopulation.buffer] will be decreased by newSize - oldSize
 * - When increasing the value AND new value more than [PanmicticPopulation.maxSize] - the existing population being copied to a new array,
 * which can take a significant amount of time. [PanmicticPopulation.buffer] will be set to 0
 * @param newBuffer Set as new [PanmicticPopulation.buffer] value if not null.
 * But [PanmicticPopulation.buffer] can be changed by changing [PanmicticPopulation.size], see above.
 * - Always must be positive or zero
 * - Manual changing [PanmicticPopulation.buffer] will copy the existing population to a new array,
 * which can take a significant amount of time
 * - When manual increasing the value - its generate new chromosomes with [Population.factory]
 * @param evaluateBuffered param for optimization. Default value is true for predictable behaviour.
 * If true - execute evaluation by [fitnessFunction] for new chromosomes in range: oldSize..<newSize
 * - No evaluations if newSize is null or newSize less than oldSize
 *
 * Set it to false for optimization by skipping double evaluation in case:
 * ```
 * evolve {
 *     // other genetic operators
 *     resize(newSize = size + 5, evaluateBuffered = false)
 *     // Danger zone: New chromosomes can be not evaluated
 *     evaluation() // evaluates in range 0..<newSize
 *     // Safe zone: all active chromosomes evaluated
 *     // other genetic operators
 * }
 * ```
 * @param parallelismLimit limit of parallel workers
 * @param fitnessFunction fitnessFunction for evaluation
 * @see
 */
public suspend fun <V, F> PanmicticLifecycle<V, F>.resize(
    newSize: Int? = null,
    newBuffer: Int? = null,
    evaluateBuffered: Boolean = true,
    parallelismLimit: Int = parallelismConfig.workersCount,
    fitnessFunction: (V) -> F = this.fitnessFunction,
) {
    if (newSize == null && newBuffer == null) return // no changes
    if (newSize == population.size && newBuffer == population.buffer) return // no changes

    val size = population.size
    population.resize(newSize, newBuffer)

    if (size >= population.size || !evaluateBuffered || !population.initialized) return // skip evaluation

    evaluateAll(
        start = size,
        end = population.size,
        parallelismLimit = parallelismLimit,
        fitnessFunction = fitnessFunction,
    )
}

/**
 * Sets [PanmicticPopulation.size] = [PanmicticPopulation.size] + [step]
 *
 * NOTE!
 * - Changing [PanmicticPopulation.size] will automatically calculate the value of [PanmicticPopulation.buffer]:
 * ```
 * evolve {
 *     var size = population.size // = 100
 *     var buffer = population.buffer // = 50
 *     adjustSize(step = 10)
 *     size = population.size // = 110
 *     buffer = population.buffer // = 40
 *     adjustSize(step = 50)
 *     // buffer limit exceeded, created new array with maxSize = 160
 *     size = population.size // = 160
 *     buffer = population.buffer // = 0 (buffer overflow)
 *     adjustSize(step = -60)
 *     size = population.size // = 100
 *     buffer = population.buffer // = 60 (buffer increased cause overflow)
 * }
 * ```
 *
 * @param step step value for new size
 * - set positive value to increase
 * - set negative value to decrease
 * - zero is skipped (no changes)
 * - When decreasing the [PanmicticPopulation.size], the array will not be copied.
 * The freed space will be marked as buffer - the value of [PanmicticPopulation.buffer] will be increased by [step]
 * - When increasing the value AND new value less or equal to [PanmicticPopulation.maxSize] - the array will not be copied.
 * Space will be taken from buffer - the value of [PanmicticPopulation.buffer] will be decreased by -[step]
 * - When increasing the value AND new value more than [PanmicticPopulation.maxSize] - the existing population being copied to a new array,
 * which can take a significant amount of time. [PanmicticPopulation.buffer] will be set to 0
 * @param evaluateBuffered param for optimization. Default value is true for predictable behaviour.
 * If true - execute evaluation by [fitnessFunction] for new chromosomes in range: oldSize..<newSize
 * - No evaluations if newSize is null or newSize less than oldSize
 *
 * Set it to false for optimization by skipping double evaluation in case:
 * ```
 * evolve {
 *     // other genetic operators
 *     adjustSize(by = 5, evaluateBuffered = false)
 *     // Danger zone: New chromosomes can be not evaluated
 *     evaluation() // evaluates in range 0..<newSize
 *     // Safe zone: all active chromosomes evaluated
 *     // other genetic operators
 * }
 * ```
 * @param parallelismLimit limit of parallel workers
 * @param fitnessFunction fitnessFunction for evaluation
 * @see resize
 */
public suspend fun <V, F> PanmicticLifecycle<V, F>.adjustSize(
    step: Int,
    evaluateBuffered: Boolean = true,
    parallelismLimit: Int = parallelismConfig.workersCount,
    fitnessFunction: (V) -> F = this.fitnessFunction,
) {
    if (step == 0) return // no changes

    val newSize = population.size + step
    require(newSize > 0) {
        "Adjust size can not decrease population.size = ${population.size} to negative or zero value with step = $step!"
    }

    val size = population.size
    population.resize(newSize, newBuffer = null)

    if (size >= population.size || !evaluateBuffered || !population.initialized) return // skip evaluation

    evaluateAll(
        start = size,
        end = population.size,
        parallelismLimit = parallelismLimit,
        fitnessFunction = fitnessFunction,
    )
}

/**
 * Sets [PanmicticPopulation.buffer] = [PanmicticPopulation.buffer] + [step]
 * @param step step value for new buffer value
 * - set positive value to increase
 * - set negative value to decrease
 * - zero is skipped (no changes)
 * - Changing [PanmicticPopulation.buffer] will copy the existing population to a new array,
 * which can take a significant amount of time
 * - When manual increasing the value - its generate new chromosomes with [Population.factory]
 * @see resize
 */
public fun <V, F> PanmicticLifecycle<V, F>.adjustBuffer(
    step: Int,
) {
    if (step == 0) return // no changes

    val newBuffer = population.buffer + step
    require(newBuffer >= 0) {
        "Adjust buffer can not decrease population.buffer = ${population.buffer} to negative value with step = $step!"
    }

    population.resize(newSize = null, newBuffer)
}
