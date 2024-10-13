package kgal

import kgal.processor.parallelism.ParallelismConfig
import kgal.processor.parallelism.ParallelismConfigScope
import kgal.statistics.StatisticsConfig
import kgal.statistics.StatisticsConfigScope

/**
 * [AbstractConfigScope] - abstract class for base implementation of [Config] with Kotlin DSL style.
 */
public abstract class AbstractConfigScope<V, F, L : Lifecycle<V, F>> : Config<V, F, L> {
    override var statisticsConfig: StatisticsConfig = StatisticsConfig()
    override var parallelismConfig: ParallelismConfig = ParallelismConfig()

    override var beforeEvolution: suspend L.() -> Unit = { }
    override var evolution: suspend L.() -> Unit = { }
    override var afterEvolution: suspend L.() -> Unit = { }

    /**
     * Creates [beforeEvolution] with specific of realization.
     * @param useDefault if true used [baseBefore] (see for specific realization)
     */
    public open fun AbstractConfigScope<V, F, L>.before(useDefault: Boolean = true, beforeEvolution: suspend L.() -> Unit) {
        this.beforeEvolution = beforeEvolution.takeIf { !useDefault } ?: { baseBefore(); beforeEvolution() }
    }

    /**
     * Creates [evolution] with specific of realization.
     * @param useDefault if true used [baseEvolve] (see for specific realization)
     */
    public open fun AbstractConfigScope<V, F, L>.evolve(useDefault: Boolean = true, evolution: suspend L.() -> Unit) {
        this.evolution = evolution.takeIf { !useDefault } ?: { baseEvolve(); evolution() }
    }

    /**
     * Creates [afterEvolution] with specific of realization.
     * @param useDefault if true used [baseAfter] (see for specific realization)
     */
    public open fun AbstractConfigScope<V, F, L>.after(useDefault: Boolean = true, afterEvolution: suspend L.() -> Unit) {
        this.afterEvolution = afterEvolution.takeIf { !useDefault } ?: { afterEvolution(); baseAfter() }
    }

    /**
     * Open property for base before Lifecycle processing
     */
    public open val baseBefore: suspend L.() -> Unit = { }

    /**
     * Open property for base evolve Lifecycle processing
     */
    public open val baseEvolve: suspend L.() -> Unit = { }

    /**
     * Open property for base after Lifecycle processing
     */
    public open val baseAfter: suspend L.() -> Unit = { }
}

/**
 * Creates [StatisticsConfig] with [StatisticsConfigScope] and apply it to the current [Config]
 */
public inline fun AbstractConfigScope<*, *, *>.statisticsConfig(
    config: StatisticsConfigScope.() -> Unit,
) {
    statisticsConfig = StatisticsConfigScope().apply(config)
}

/**
 * Creates [ParallelismConfig] with [ParallelismConfigScope] and apply it to the current [Config]
 */
public inline fun AbstractConfigScope<*, *, *>.parallelismConfig(
    config: ParallelismConfigScope.() -> Unit,
) {
    parallelismConfig = ParallelismConfigScope().apply(config)
}
