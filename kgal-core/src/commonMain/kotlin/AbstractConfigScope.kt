package kgal

import kgal.processor.parallelism.ParallelismConfig
import kgal.processor.parallelism.ParallelismConfigScope
import kgal.statistics.StatisticsConfig
import kgal.statistics.StatisticsConfigScope
import kgal.statistics.TimeStore
import kotlin.random.Random

/**
 * [AbstractConfigScope] - abstract class for base implementation of [Config] with Kotlin DSL style.
 */
public abstract class AbstractConfigScope<V, F, ES : EvolveScope<V, F>> : Config<V, F, ES> {

    override var random: Random = Random

    override var statisticsConfig: StatisticsConfig = StatisticsConfig()

    override var parallelismConfig: ParallelismConfig = ParallelismConfig()

    override var timeStore: TimeStore = TimeStore()

    override var beforeEvolution: suspend ES.() -> Unit = { }

    override var evolution: suspend ES.() -> Unit = { }

    override var afterEvolution: suspend ES.() -> Unit = { }
}

/**
 * Creates [StatisticsConfig] with [StatisticsConfigScope] and apply it to the current [Config].
 *
 * Example:
 * ```
 * specificGA {
 *     // configure statistics of GA
 *     statisticsConfig {
 *         replay = 0
 *         extraBufferCapacity = 1000
 *         onBufferOverflow = BufferOverflow.SUSPEND
 *         coroutineContext = Dispatchers.IO
 *         enableDefaultCollector = false
 *         defaultCollector = FlowCollector(::println)
 *     }
 *
 *     // configure specific params of GA
 * }
 * ```
 */
public inline fun AbstractConfigScope<*, *, *>.statisticsConfig(
    config: StatisticsConfigScope.() -> Unit,
) {
    statisticsConfig = StatisticsConfigScope().apply(config)
}

/**
 * Creates [ParallelismConfig] with [ParallelismConfigScope] and apply it to the current [Config].
 *
 * Example:
 * ```
 * specificGA {
 *     // configure parallelism of GA
 *     parallelismConfig {
 *         workersCount = 6
 *         dispatcher = Dispatchers.Default
 *     }
 *
 *     // configure specific params of GA
 * }
 * ```
 */
public inline fun AbstractConfigScope<*, *, *>.parallelismConfig(
    config: ParallelismConfigScope.() -> Unit,
) {
    parallelismConfig = ParallelismConfigScope().apply(config)
}
