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
public abstract class AbstractConfigScope<V, F, L : Lifecycle<V, F>> : Config<V, F, L> {

    override var random: Random = Random

    override var statisticsConfig: StatisticsConfig = StatisticsConfig()

    override var parallelismConfig: ParallelismConfig = ParallelismConfig()

    override var timeStore: TimeStore = TimeStore()

    override var beforeEvolution: suspend L.() -> Unit = { }
    override var evolution: suspend L.() -> Unit = { }
    override var afterEvolution: suspend L.() -> Unit = { }

    /**
     * Creates [beforeEvolution] with specific of realization.
     * Callback before evolution process. Executed only once at launch if [GA.iteration] is 0.
     * @param useDefault if true used [baseBefore] (see for specific realization)
     */
    public open fun AbstractConfigScope<V, F, L>.before(
        useDefault: Boolean = true,
        beforeEvolution: suspend L.() -> Unit,
    ) {
        this.beforeEvolution = beforeEvolution.takeIf { !useDefault } ?: { baseBefore(); beforeEvolution() }
    }

    /**
     * Creates [evolution] with specific of realization.
     *
     * Kgal understand [evolution] as a function in [Lifecycle] scope.
     * When [GA] starts to evolve, its creates an infinite loop. There are 3 main actions that occur in this loop:
     * - Increment [GA.iteration] by one
     * - Execute [evolution]
     * - Check stops (If any stop rule active break infinite loop)
     *
     * Thus, the [evolution] function corresponds to one iteration of the genetic algorithm.
     * However, thanks to [Lifecycle] scope, this function has the ability to change dynamically depending on various conditions,
     * which makes it easy to create unique, flexible evolution strategies, for example:
     * - create a dependence of the mutation chance on the iteration (see example)
     * - create evolutionary stages (e.g. depending on iteration)
     * that will describe different evolutionary strategies (use different genetic operators)
     * - create absolutely unique genetic operators - the entire population is at your complete disposal!
     * - calculate, compare, change!
     *
     * Base example:
     * ```
     * // init Specific GA
     * specificGA {
     *     // configure specific params of GA
     *
     *     // create evolution with evolve
     *     evolve(useDefault = true) { (this = SpecificLifecycle)
     *         // An example of a basic evolutionary strategy
     *         val currentIteration = iteration
     *         selTournament(size = 3, parallelismLimit = NO_PARALLELISM)
     *         cxOnePoint(chance = 0.8)
     *         mutFlipBit(chance = 0.2, flipBitChance = 0.01 * iteration) // on maxIteration: flipBitChance = 0.5
     *         evaluation()
     *         stopBy(maxIteration = 50) { bestFitness == 100 }
     *     }
     * }
     * ```
     * @param useDefault if true used [baseEvolve] (see for specific realization)
     */
    public open fun AbstractConfigScope<V, F, L>.evolve(useDefault: Boolean = true, evolution: suspend L.() -> Unit) {
        this.evolution = evolution.takeIf { !useDefault } ?: { baseEvolve(); evolution() }
    }

    /**
     * Creates [afterEvolution] with specific of realization.
     * Callback after evolution process. Executed at launch when [GA.state] is going to be [State.FINISHED].
     * @param useDefault if true used [baseAfter] (see for specific realization)
     */
    public open fun AbstractConfigScope<V, F, L>.after(
        useDefault: Boolean = true,
        afterEvolution: suspend L.() -> Unit,
    ) {
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
 * Creates [ParallelismConfig] with [ParallelismConfigScope] and apply it to the current [Config]
 *
 * Example:
 * ```
 * specificGA {
 *     // configure parallelism of GA
 *     parallelismConfig {
 *         workersCount = 6
 *         dispatcher = Dispatchers.Unconfined
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
