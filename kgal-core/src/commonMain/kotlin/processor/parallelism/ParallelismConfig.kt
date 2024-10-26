package kgal.processor.parallelism

import kgal.dsl.ConfigDslMarker
import kgal.processor.parallelism.ParallelismConfig.Companion.NO_PARALLELISM
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal const val DEFAULT_PARALLEL_WORKER_COUNT = 0
internal val DEFAULT_PARALLEL_DISPATCHER = Dispatchers.Default

/**
 * Configuration of basic parameters for parallel processing.
 * Please note that parallel computing is only justified for really complex operations.
 * Computationally simple genetic operators such as selection or mutation may be more efficient to execute serially than in parallel.
 *
 * If you really need parallelism for your computations,
 * you can manually limit the execution of simple genetic operators by using [NO_PARALLELISM] to improve performance:
 * ```
 * evolution {
 *     selTournament(size = 3, parallelismLimit = NO_PARALLELISM)
 * }
 *
 * ```
 * Creates with [ParallelismConfig].
 * @see ParallelismConfigScope
 */
@ConfigDslMarker
public interface ParallelismConfig {

    /**
     * workersCount - the number of launched coroutines which can be executed in parallel processing.
     *
     * Default value is [DEFAULT_PARALLEL_WORKER_COUNT].
     */
    public val workersCount: Int

    /**
     * dispatcher - dispatcher for parallel coroutines.
     *
     * Default value is [DEFAULT_PARALLEL_DISPATCHER].
     */
    public val dispatcher: CoroutineDispatcher

    public companion object {

        /**
         * Flag for limiting parallelism, which sets the execution of the genetic operator to be strictly sequential.
         */
        public const val NO_PARALLELISM: Int = 0
    }
}

/**
 * Property for displaying availability of parallel mode of operation.
 */
public inline val ParallelismConfig.enabled: Boolean
    get() = workersCount > 1

/**
 * Creates [ParallelismConfig].
 * @see [ParallelismConfig]
 */
public fun ParallelismConfig(
    workersCount: Int = DEFAULT_PARALLEL_WORKER_COUNT,
    dispatcher: CoroutineDispatcher = DEFAULT_PARALLEL_DISPATCHER,
): ParallelismConfig = ParallelismConfigScope(workersCount, dispatcher)

/**
 * Deep clones the current parallelism configuration.
 */
public fun ParallelismConfig.clone(): ParallelismConfig = ParallelismConfigScope(
    workersCount = workersCount,
    dispatcher = dispatcher,
)

/**
 * ParallelismConfigScope - Creating [ParallelismConfig] with Kotlin DSL style.
 */
public class ParallelismConfigScope(
    workersCount: Int = DEFAULT_PARALLEL_WORKER_COUNT,
    override var dispatcher: CoroutineDispatcher = DEFAULT_PARALLEL_DISPATCHER,
) : ParallelismConfig {
    override var workersCount: Int = workersCount
        set(value) {
            require(value > 1) { "Illegal count of workers for ParallelismConfig. Count must be at least 2" }
            field = value
        }
}
