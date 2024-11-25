package kgal.statistics

import kgal.statistics.StatisticsProvider.Companion.DEFAULT_COLLECTOR_ID
import kgal.statistics.StatisticsProvider.Companion.STAT_STOP_COLLECT_FLAG
import kgal.statistics.note.SingleStatisticNote
import kgal.statistics.note.Statistic
import kgal.statistics.note.StatisticNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

public typealias STAT_COLLECTOR = FlowCollector<StatisticNote<Any?>>
public typealias STAT_COLLECTOR_SCOPE = suspend CoroutineScope.(stat: Flow<StatisticNote<Any?>>) -> Unit

/**
 * [StatisticsProvider] - interface for Statistics Provider based on [SharedFlow].
 */
public interface StatisticsProvider {

    /**
     * Add collector for statistics information.
     * It will be activated in [prepareStatistics] function if it has not been removed from [StatisticsProvider].
     *
     * Create with [StatisticsProvider] function
     * @param id unique ID for collector
     * @param collector collector for [SharedFlow] based on [STAT_COLLECTOR_SCOPE]
     * @see STAT_COLLECTOR_SCOPE
     * @see StatisticsProviderInstance
     */
    public fun collect(id: String, collector: STAT_COLLECTOR_SCOPE)

    /**
     * Emit [StatisticNote] to [StatisticsProvider].
     */
    public suspend fun emit(value: StatisticNote<Any?>)

    /**
     * Preparing statistics - launch active collectors.
     */
    public fun prepareStatistics()

    /**
     * Stop all collectors for this [StatisticsProvider].
     * @param force If true immediately stop collectors, data can be missed!
     * If false waits for all collectors to handle statistics, after that stop collectors
     */
    public suspend fun stopCollectors(force: Boolean)

    /**
     * Unsubscribe and remove collector by [id].
     */
    public suspend fun removeCollector(id: String)

    /**
     * Unsubscribe and clear all collectors for [StatisticsProvider].
     */
    public suspend fun clearCollectors()

    public companion object {
        public const val DEFAULT_COLLECTOR_ID: String = "DEFAULT_COLLECTOR_ID"
        public const val BASE_COLLECTOR_ID: String = "BASE_COLLECTOR_ID"

        /**
         * A special flag, upon receipt of which the collection of the statistics stream stops.
         */
        public const val STAT_STOP_COLLECT_FLAG: String = "STAT_STOP_COLLECT_FLAG"
    }
}

/**
 * Creates [StatisticsProvider] for [ownerName] by [statisticsConfig].
 */
public fun StatisticsProvider(
    ownerName: String,
    statisticsConfig: StatisticsConfig,
): StatisticsProvider = StatisticsProviderInstance(ownerName, statisticsConfig)

/**
 * Base instance of [StatisticsProvider].
 */
internal class StatisticsProviderInstance(
    private val ownerName: String,
    private val statisticsConfig: StatisticsConfig,
) : StatisticsProvider {

    /**
     * Statistics [SharedFlow] for provider. Contains all values of statistics for [ownerName].
     * Configured by [statisticsConfig] params
     */
    private val statistics: MutableSharedFlow<StatisticNote<Any?>> = statisticsConfig.flow

    /**
     * Map for all collectors of this [StatisticsProvider].
     */
    private val collectors: MutableMap<String, STAT_COLLECTOR_SCOPE> = collectors()

    /**
     * Special [CoroutineScope] for control all collectors' behavior.
     */
    private var statisticsCoroutineScope: CoroutineScope = statisticsConfig.newCoroutineScope
        get() {
            if (field.coroutineContext.job.isCancelled) {
                field = statisticsConfig.newCoroutineScope
            }
            return field
        }

    override fun collect(id: String, collector: STAT_COLLECTOR_SCOPE) {
        if (collectors.contains(id))
            throw IllegalArgumentException("Collector with id $id has been already added, try to use unique ID")

        collectors[id] = collector
    }

    override suspend fun emit(value: StatisticNote<Any?>) =
        statistics.emit(value)

    override fun prepareStatistics() {
        with(statisticsCoroutineScope) {
            collectors.forEach { (id, collector) ->
                launch {
                    collector(statisticsSafeWrapper(collectorId = id))
                }
            }
        }
    }

    override suspend fun stopCollectors(force: Boolean) {
        if (!force) {
            // send STAT_STOP_COLLECT_FLAG as a terminal for all collectors
            emitStopCollectFlag(collectorId = null)
            // wait for all collectors of statistics completed
            statisticsCoroutineScope.coroutineContext.job.children.forEach { it.join() }
        }
        // cancel statisticsCoroutineScope
        statisticsCoroutineScope.cancel()
    }

    override suspend fun removeCollector(id: String) {
        emitStopCollectFlag(collectorId = id)
        collectors.remove(id)
    }

    override suspend fun clearCollectors() {
        emitStopCollectFlag(collectorId = null)
        collectors.clear()
    }

    /**
     * A special [statistics] flow wrapper used [kotlinx.coroutines.flow.takeWhile] for stop collecting values.
     */
    private fun statisticsSafeWrapper(collectorId: String): Flow<StatisticNote<Any?>> =
        statistics.takeWhile { note ->
            val isFinished =
                note.statistic.name == STAT_STOP_COLLECT_FLAG && note.statistic.value?.equals(collectorId) ?: true
            !isFinished
        }

    /**
     * Initializes collectors depending on [StatisticsConfig].
     */
    private fun collectors(): MutableMap<String, STAT_COLLECTOR_SCOPE> =
        if (statisticsConfig.enableDefaultCollector) {
            mutableMapOf(DEFAULT_COLLECTOR_ID to statisticsConfig.defaultCollector)
        } else {
            mutableMapOf()
        }

    /**
     * Send STAT_STOP_COLLECT_FLAG as a terminal for collectors.
     * @param collectorId if null stop all collectors else stop collector by [collectorId]
     */
    private suspend fun emitStopCollectFlag(collectorId: String?) {
        emit(
            SingleStatisticNote(
                statistic = Statistic(name = STAT_STOP_COLLECT_FLAG, value = collectorId),
                ownerName = ownerName,
                iteration = -1,
            )
        )
    }
}
