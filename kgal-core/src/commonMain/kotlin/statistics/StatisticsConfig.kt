package kgal.statistics

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

/**
 * Configuration of basic parameters for statistics operation
 *
 * Creates with [StatisticsConfig] or [statisticsConfig]
 * @see StatisticsConfigScope
 */
public interface StatisticsConfig {

    /**
     * replay - the number of values replayed to new subscribers (cannot be negative, defaults to zero).
     *
     * Default value is [DEFAULT_REPLAY]
     */
    public val replay: Int

    /**
     * extraBufferCapacity - the number of values buffered in addition to replay.
     * Emit does not suspend while there is a buffer space remaining (optional, cannot be negative, defaults to zero).
     *
     * Default value is [DEFAULT_EXTRA_BUFFER_CAPACITY]
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
     * coroutineContext - context of coroutine for statistics
     *
     * Default value is [DEFAULT_COROUTINE_CONTEXT]
     */
    public val coroutineContext: CoroutineContext

    /**
     * enableDefaultCollector - flag for turn on [defaultCollector], true for default
     *
     * Default value is [DEFAULT_ENABLE_DEFAULT_COLLECTOR]
     */
    public val enableDefaultCollector: Boolean

    /**
     * defaultCollector - println base statistics to console if [enableDefaultCollector] is true
     * @see DEFAULT_COLLECTOR
     *
     * Default value is [DEFAULT_COLLECTOR]
     */
    public val defaultCollector: FlowCollector<StatisticNote<Any?>>
}

/**
 * Creates [StatisticsConfig]
 * @see [StatisticsConfig]
 */
public fun StatisticsConfig(
    replay: Int = DEFAULT_REPLAY,
    extraBufferCapacity: Int = DEFAULT_EXTRA_BUFFER_CAPACITY,
    onBufferOverflow: BufferOverflow = DEFAULT_ON_BUFFER_OVERFLOW,
    coroutineContext: CoroutineContext = DEFAULT_COROUTINE_CONTEXT,
    enableDefaultCollector: Boolean = DEFAULT_ENABLE_DEFAULT_COLLECTOR,
    defaultCollector: FlowCollector<StatisticNote<Any?>> = DEFAULT_COLLECTOR,
): StatisticsConfig =
    StatisticsConfigScope(
        replay = replay,
        extraBufferCapacity = extraBufferCapacity,
        onBufferOverflow = onBufferOverflow,
        coroutineContext = coroutineContext,
        enableDefaultCollector = enableDefaultCollector,
        defaultCollector = defaultCollector,
    )

/**
 * Creates [StatisticsConfig] with [StatisticsConfigScope]
 * @see StatisticsConfig
 * @see StatisticsConfigScope
 */
public inline fun statisticsConfig(config: StatisticsConfigScope.() -> Unit): StatisticsConfig =
    StatisticsConfigScope().apply(config)

/**
 * Creates a new [MutableSharedFlow] for [StatisticNote] with [StatisticsConfig] params
 */
public val StatisticsConfig.flow: MutableSharedFlow<StatisticNote<Any?>>
    get() = MutableSharedFlow(replay, extraBufferCapacity, onBufferOverflow)

/**
 * Creates a new [CoroutineScope] with [SupervisorJob] and [StatisticsConfig.coroutineContext]
 */
public val StatisticsConfig.newCoroutineScope: CoroutineScope
    get() = CoroutineScope(SupervisorJob() + coroutineContext)

/**
 * StatisticsConfigScope - Creating [StatisticsConfig] with Kotlin DSL style
 * @see statisticsConfig
 */
public class StatisticsConfigScope(
    override var replay: Int = DEFAULT_REPLAY,
    override var extraBufferCapacity: Int = DEFAULT_EXTRA_BUFFER_CAPACITY,
    override var onBufferOverflow: BufferOverflow = DEFAULT_ON_BUFFER_OVERFLOW,
    override var coroutineContext: CoroutineContext = DEFAULT_COROUTINE_CONTEXT,
    override var enableDefaultCollector: Boolean = DEFAULT_ENABLE_DEFAULT_COLLECTOR,
    override var defaultCollector: FlowCollector<StatisticNote<Any?>> = DEFAULT_COLLECTOR,
) : StatisticsConfig
