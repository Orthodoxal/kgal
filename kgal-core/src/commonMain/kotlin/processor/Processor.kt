package kgal.processor

import kgal.Lifecycle
import kgal.processor.parallelism.enabled
import kgal.utils.loop
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Process action with [sequential] or [parallel] mode.
 * @param parallelismLimit limit of parallel workers
 * @param sequential sequential execution instruction
 * @param parallel parallel execution instruction
 */
public inline fun Lifecycle<*, *>.process(
    parallelismLimit: Int,
    sequential: () -> Unit,
    parallel: () -> Unit,
) {
    if (parallelismConfig.enabled && parallelismLimit > 0) {
        parallel()
    } else {
        sequential()
    }
}

/**
 * Iterative process action from [startIteration] to [endIteration].
 * @param parallelismLimit limit of parallel workers
 * @param action action will be executed in sequential or parallel mode
 */
public suspend inline fun Lifecycle<*, *>.process(
    parallelismLimit: Int,
    startIteration: Int,
    endIteration: Int,
    crossinline action: suspend (iteration: Int, random: Random) -> Unit,
) {
    process(
        parallelismLimit = parallelismLimit,
        sequential = {
            loop(startIteration, endIteration) { index -> action(index, random) }
        },
        parallel = {
            parallelProcess(parallelismLimit, startIteration, endIteration, action)
        }
    )
}

/**
 * Iterative process action from [startIteration] to [endIteration] with [step].
 * @param parallelismLimit limit of parallel workers
 * @param action action will be executed in sequential or parallel mode
 */
public suspend inline fun Lifecycle<*, *>.process(
    parallelismLimit: Int,
    startIteration: Int,
    endIteration: Int,
    step: Int,
    crossinline action: suspend (iteration: Int, random: Random) -> Unit,
) {
    process(
        parallelismLimit = parallelismLimit,
        sequential = {
            loop(startIteration, endIteration, step) { index -> action(index, random) }
        },
        parallel = {
            parallelProcess(parallelismLimit, startIteration, endIteration, step, action)
        }
    )
}

/**
 * Iterative parallel process action from [startIteration] to [endIteration].
 * @param parallelismLimit limit of parallel workers
 * @param action action will be executed on parallel mode
 */
public suspend inline fun Lifecycle<*, *>.parallelProcess(
    parallelismLimit: Int,
    startIteration: Int,
    endIteration: Int,
    crossinline action: suspend (iteration: Int, random: Random) -> Unit,
) {
    coroutineScope {
        loop(start = 0, end = parallelismLimit) { workerIndex ->
            val workerRandom = Random(seed = random.nextInt())
            launch(parallelismConfig.dispatcher) {
                var index = workerIndex + startIteration
                while (index < endIteration) {
                    action(index, workerRandom)
                    index += parallelismLimit
                }
            }
        }
    }
}

/**
 * Iterative parallel process action from [startIteration] to [endIteration] with [step].
 * @param parallelismLimit limit of parallel workers
 * @param action action will be executed on parallel mode
 */
public suspend inline fun Lifecycle<*, *>.parallelProcess(
    parallelismLimit: Int,
    startIteration: Int,
    endIteration: Int,
    step: Int,
    crossinline action: suspend (iteration: Int, random: Random) -> Unit,
) {
    coroutineScope {
        val workerStep = step * parallelismLimit
        loop(start = 0, end = parallelismLimit) { workerIndex ->
            val workerRandom = Random(seed = random.nextInt())
            launch(parallelismConfig.dispatcher) {
                var index = workerIndex * step + startIteration
                while (index < endIteration) {
                    action(index, workerRandom)
                    index += workerStep
                }
            }
        }
    }
}
