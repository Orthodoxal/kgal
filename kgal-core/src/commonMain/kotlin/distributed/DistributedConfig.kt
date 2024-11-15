package kgal.distributed

import kgal.*
import kgal.distributed.operators.launchChildren
import kgal.dsl.ConfigDslMarker
import kgal.operators.stopBy

/**
 * Describes the configuration parameters necessary for the operation of the [DistributedGA].
 * @see DistributedConfigScope
 */
public interface DistributedConfig<V, F> : Config<V, F, DistributedLifecycle<V, F>> {

    /**
     * Child GAs of [DistributedGA]. Mutable with [add] and [removeAt].
     */
    public val children: List<GA<V, F>>

    /**
     * Special container for creating and adding children to [DistributedConfig].
     */
    public val populationMultiFactory: DistributedPopulationMultiFactory

    /**
     * Adds the [GA] to the end of [children].
     */
    public fun add(ga: GA<V, F>)

    /**
     * Inserts a [GA] into [children] at the specified [index].
     */
    public fun add(index: Int, ga: GA<V, F>)

    /**
     * Removes a [GA] at the specified index from [children].
     */
    public fun removeAt(index: Int): GA<V, F>

    /**
     * Unary operator for adding [GA] to [children].
     * ```
     * val ga: GA<*, *> = ...
     * dGA {
     *     +ga
     *     // OR
     *     +pGA(...) {
     *     ...
     *     }
     *     +сGA(...) {
     *     ...
     *     }
     * }
     * ```
     */
    public operator fun GA<V, F>.unaryPlus(): GA<V, F> = apply { add(this) }

    /**
     * Unary operator for adding list of [GA]s to [children].
     * ```
     * val list: List<GA<*, *>> = ...
     * dGA {
     *     +list
     *     // OR
     *     +pGAs(...) {
     *     ...
     *     }
     *     +сGAs(...) {
     *     ...
     *     }
     * }
     * ```
     */
    public operator fun List<GA<V, F>>.unaryPlus(): List<GA<V, F>> = onEach { add(it) }

    /**
     * Override base population as [DistributedPopulation] for [DistributedGA].
     */
    public override val population: DistributedPopulation<V, F>
}

/**
 * Implementation of [DistributedConfig] based on [AbstractConfigScope].
 * Params factory and [fitnessFunction] are considered mandatory.
 * @param factory default population factory for child GAs
 * @param fitnessFunction default fitness function for evaluation step
 * @param populationName name for [DistributedPopulation]
 * @param children child GA of [DistributedGA]
 */
public class DistributedConfigScope<V, F>(
    factory: PopulationFactory<V, F>,
    override var fitnessFunction: (V) -> F,
    populationName: String,
    children: List<GA<V, F>>,
) : DistributedConfig<V, F>, AbstractConfigScope<V, F, DistributedLifecycle<V, F>>() {

    override val children: MutableList<GA<V, F>> = children.toMutableList()

    override val populationMultiFactory: DistributedPopulationMultiFactory =
        DistributedPopulationMultiFactory()

    override val population: DistributedPopulation<V, F> =
        population(
            name = populationName,
            factory = factory,
            subpopulations = if (children.isNotEmpty()) {
                MutableList(children.size) { children[it].population }
            } else mutableListOf(),
        )

    override fun add(ga: GA<V, F>) {
        children.add(ga)
        population.addPopulation(ga.population)
    }

    override fun add(index: Int, ga: GA<V, F>) {
        children.add(index, ga)
        population.addPopulation(index, ga.population)
    }

    override fun removeAt(index: Int): GA<V, F> {
        population.removePopulationAt(index)
        return children.removeAt(index)
    }

    /**
     * Launch all child [GA]s with [launchChildren] operator.
     * @see launchChildren
     */
    public val baseEvolve: suspend DistributedLifecycle<V, F>.() -> Unit = {
        launchChildren(parallelismConfig.workersCount)
    }

    override var evolution: suspend DistributedLifecycle<V, F>.() -> Unit = baseEvolve
}

/**
 * Callback before evolution process which will be invoked if [GA.iteration] is 0.
 *
 * Example:
 * ```
 * // init DistributedGA
 * dGA(
 *     // configure common factory, fitnessFunction, population name and children (if already exist)
 * ) {
 *     // set ga's configuration here (adding children)
 *
 *     before { (this = DistributedLifecycle)
 *         println("GA STARTED, initial iteration is $iteration")
 *     }
 * }
 * ```
 */
public fun <V, F> DistributedConfigScope<V, F>.before(
    beforeEvolution: suspend (@ConfigDslMarker DistributedLifecycle<V, F>).() -> Unit,
) {
    this.beforeEvolution = beforeEvolution
}

/**
 * Applies `evolutionary strategy` for [DistributedGA] (The most famous type of [DistributedGA] is the `Island Generic Algorithm`)
 * as [evolution] function in [DistributedLifecycle] scope that includes the process of launching
 * child [GA]s ([DistributedGA.children]) and processes of interaction between subpopulations.
 * - `evolutionary strategy` of [DistributedGA] is applied to child [GA]s
 * - `island evolutionary strategy` looks like:
 * `independent launch child GAs`→`waiting all GAs to be finished`→`migration between subpopulations`
 *
 * When [DistributedGA] starts to evolve, its creates an `infinite loop` for your `evolutionary strategy` function.
 * There are 3 main actions that occur in this loop:
 * - Increment [GA.iteration] by one
 * - Execute [evolution]
 * - Check stops (If any stop rule is active - breaks infinite loop)
 *
 * Example of creating base `island evolutionary strategy` with [evolve] function:
 * ```
 * // init DistributedGA
 * dGA(
 *     // configure common factory, fitnessFunction, population name and children (if already exist)
 * ) {
 *     // set ga's configuration here (adding children)
 *
 *     // set evolutionary strategy with evolve
 *     evolve { (this = DistributedLifecycle)
 *         // don't forget to set a limit on the number of iterations
 *         // otherwise the evolution will happen endlessly
 *         stopBy(maxIteration = 5)
 *         migration(percent = 0.1)
 *     }
 * }
 * ```
 * @param useDefault if true (default) used [DistributedConfigScope.baseEvolve]:
 * @see before
 * @see after
 * @see stopBy
 */
public fun <V, F> DistributedConfigScope<V, F>.evolve(
    useDefault: Boolean = true,
    evolution: suspend (@ConfigDslMarker DistributedLifecycle<V, F>).() -> Unit,
) {
    this.evolution = evolution.takeIf { !useDefault } ?: { baseEvolve(); evolution() }
}

/**
 * Callback after evolution process which will be invoked when [GA.state] is going to be [State.FINISHED].
 *
 * Example:
 * ```
 * // init DistributedGA
 * dGA(
 *     // configure common factory, fitnessFunction, population name and children (if already exist)
 * ) {
 *     // set ga's configuration here (adding children)
 *
 *     after { (this = DistributedLifecycle)
 *         println("DistributedGA is going to be FINISHED")
 *     }
 * }
 * ```
 */
public fun <V, F> DistributedConfigScope<V, F>.after(
    afterEvolution: suspend (@ConfigDslMarker DistributedLifecycle<V, F>).() -> Unit,
) {
    this.afterEvolution = afterEvolution
}
