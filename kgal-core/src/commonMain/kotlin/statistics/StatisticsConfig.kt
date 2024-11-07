package kgal.statistics

import kgal.dsl.ConfigDslMarker
import kgal.statistics.note.StatisticNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.CoroutineContext

internal const val DEFAULT_REPLAY: Int = 0
internal const val DEFAULT_EXTRA_BUFFER_CAPACITY: Int = 100
internal val DEFAULT_ON_BUFFER_OVERFLOW: BufferOverflow = BufferOverflow.SUSPEND
internal val DEFAULT_COROUTINE_CONTEXT: CoroutineContext = Dispatchers.IO
internal const val DEFAULT_ENABLE_DEFAULT_COLLECTOR: Boolean = true
internal val DEFAULT_COLLECTOR: FlowCollector<StatisticNote<Any?>> = FlowCollector(::println)
internal const val DEFAULT_GUARANTEED_SORTED: Boolean = false

/**
 * Configuration of basic parameters for statistics operation.
 *
 * Creates with [StatisticsConfig].
 * @see StatisticsConfigScope
 */
public interface StatisticsConfig {

    /**
     * replay - the number of values replayed to new subscribers (cannot be negative, defaults to zero).
     *
     * Default value is [DEFAULT_REPLAY].
     */
    public val replay: Int

    /**
     * extraBufferCapacity - the number of values buffered in addition to replay.
     * Emit does not suspend while there is a buffer space remaining (optional, cannot be negative, defaults to zero).
     *
     * Default value is [DEFAULT_EXTRA_BUFFER_CAPACITY].
     */
    public val extraBufferCapacity: Int

    /**
     * onBufferOverflow - configures an emit action on buffer overflow. Optional, defaults to suspending attempts to emit a value.
     * Values other than BufferOverflow. SUSPEND are supported only when replay > 0 or extraBufferCapacity > 0.
     * Buffer overflow can happen only when there is at least one subscriber that is not ready to accept the new value.
     * In the absence of subscribers only the most recent replay values are stored and the buffer overflow behavior is never triggered and has no effect.
     *
     * Default value is [DEFAULT_ON_BUFFER_OVERFLOW]
     */
    public val onBufferOverflow: BufferOverflow

    /**
     * coroutineContext - context of coroutine for statistics.
     *
     * Default value is [DEFAULT_COROUTINE_CONTEXT].
     */
    public val coroutineContext: CoroutineContext

    /**
     * enableDefaultCollector - flag for turn on [defaultCollector], true for default.
     *
     * Default value is [DEFAULT_ENABLE_DEFAULT_COLLECTOR].
     */
    public val enableDefaultCollector: Boolean

    /**
     * defaultCollector - println base statistics to console if [enableDefaultCollector] is true.
     *
     * Default value is [DEFAULT_COLLECTOR],
     */
    public val defaultCollector: FlowCollector<StatisticNote<Any?>>

    /**
     * Optimization flag for statistics. Default is false.
     *
     * NOTE:
     * - it is not recommended to use this optimization for cGA
     * - use it if you expect performance gains for statistical operators when getting a sorted population, see example below
     *
     * If true:
     * - executes Best, Worst, Mean with O(1)
     * - statistics operators change their implementation - waiting to receive a sorted population in descending order only
     * - executes it only in safe zones of evolve strategy (see Example below)
     *
     * If false:
     * - executes Best, Worst with O(N), Mean with O(N*log(N)).
     * - order in population is irrelevant
     *
     * Example:
     * ```
     * statisticsConfig {
     *     guaranteedSorted = true // Set flag to true
     * }
     *
     * // create evolution strategy
     * evolve {
     *     // other genetic operators
     *     // Danger zone: the order of chromosomes is unknown
     *     evaluation(sortAfter = true) // evaluate population and sort in descending order
     *     // Safe zone: population sorted by descending
     *     stat(best(), worst()) // OK but inefficient:
     *     // without sorting: O(N) + O(N)
     *     // with sorting: O(N*log(N)) + O(1) + O(1)
     *
     *     // other genetic operators
     *     // Danger zone: the order of chromosomes is unknown
     *     population.sort()
     *     // Safe zone: population sorted by descending
     *     stat(best(), mean(), worst()) // OK and efficient
     *     // without sorting: O(N) + O(N*log(N)) + O(N)
     *     // with sorting: O(N*log(N)) + O(1) + O(1)
     * }
     * ```
     * @see DEFAULT_GUARANTEED_SORTED
     */
    public val guaranteedSorted: Boolean
}

/**
 * Creates [StatisticsConfig].
 * @see [StatisticsConfig]
 */
public fun StatisticsConfig(
    replay: Int = DEFAULT_REPLAY,
    extraBufferCapacity: Int = DEFAULT_EXTRA_BUFFER_CAPACITY,
    onBufferOverflow: BufferOverflow = DEFAULT_ON_BUFFER_OVERFLOW,
    coroutineContext: CoroutineContext = DEFAULT_COROUTINE_CONTEXT,
    enableDefaultCollector: Boolean = DEFAULT_ENABLE_DEFAULT_COLLECTOR,
    defaultCollector: FlowCollector<StatisticNote<Any?>> = DEFAULT_COLLECTOR,
    guaranteedSorted: Boolean = DEFAULT_GUARANTEED_SORTED,
): StatisticsConfig =
    StatisticsConfigScope(
        replay = replay,
        extraBufferCapacity = extraBufferCapacity,
        onBufferOverflow = onBufferOverflow,
        coroutineContext = coroutineContext,
        enableDefaultCollector = enableDefaultCollector,
        defaultCollector = defaultCollector,
        guaranteedSorted = guaranteedSorted,
    )

/**
 * Creates a new [MutableSharedFlow] for [StatisticNote] with [StatisticsConfig] params.
 */
public val StatisticsConfig.flow: MutableSharedFlow<StatisticNote<Any?>>
    get() = MutableSharedFlow(replay, extraBufferCapacity, onBufferOverflow)

/**
 * Creates a new [CoroutineScope] with [SupervisorJob] and [StatisticsConfig.coroutineContext]
 */
public val StatisticsConfig.newCoroutineScope: CoroutineScope
    get() = CoroutineScope(SupervisorJob() + coroutineContext)

/**
 * StatisticsConfigScope - Creating [StatisticsConfig] with Kotlin DSL style.
 */
@ConfigDslMarker
public class StatisticsConfigScope(
    override var replay: Int = DEFAULT_REPLAY,
    override var extraBufferCapacity: Int = DEFAULT_EXTRA_BUFFER_CAPACITY,
    override var onBufferOverflow: BufferOverflow = DEFAULT_ON_BUFFER_OVERFLOW,
    override var coroutineContext: CoroutineContext = DEFAULT_COROUTINE_CONTEXT,
    override var enableDefaultCollector: Boolean = DEFAULT_ENABLE_DEFAULT_COLLECTOR,
    override var defaultCollector: FlowCollector<StatisticNote<Any?>> = DEFAULT_COLLECTOR,
    override var guaranteedSorted: Boolean = DEFAULT_GUARANTEED_SORTED,
) : StatisticsConfig
