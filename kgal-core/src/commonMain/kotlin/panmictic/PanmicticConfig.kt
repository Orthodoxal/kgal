package kgal.panmictic

import kgal.*
import kgal.dsl.ConfigDslMarker
import kgal.operators.stopBy
import kgal.panmictic.operators.crossover.crossover
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.fillPopulationIfNeed
import kgal.panmictic.operators.mutation.mutation
import kgal.panmictic.operators.selection.selection
import kgal.parallelismConfig
import kgal.processor.parallelism.ParallelismConfig
import kgal.processor.parallelism.ParallelismConfigScope
import kgal.processor.process

/**
 * Describes the configuration parameters necessary for the operation of the [PanmicticGA].
 * @see PanmicticConfigScope
 */
public interface PanmicticConfig<V, F> : Config<V, F, PanmicticEvolveScope<V, F>> {

    /**
     * Number of elite chromosomes - the best chromosomes of the population, which have privileged rights:
     * - recalculates on each evaluation stage and moved to start of [PanmicticPopulation]
     * - guaranteed to pass the selection stage
     * - at the crossing stage they cannot be changed or replaced,
     * but they actively participate in the creation of a new generation by changing other chromosomes
     * - do not change during the mutation stage
     * @see [PanmicticEvolveScope.evaluation]
     */
    public val elitism: Int

    /**
     * Override base population as [PanmicticPopulation] for [PanmicticGA].
     */
    public override val population: PanmicticPopulation<V, F>
}

/**
 * Implementation of [PanmicticConfig] based on [AbstractConfigScope].
 * Params [population] and [fitnessFunction] are considered mandatory.
 */
public class PanmicticConfigScope<V, F>(
    override val population: PanmicticPopulation<V, F>,
    override var fitnessFunction: (V) -> F,
) : PanmicticConfig<V, F>, AbstractConfigScope<V, F, PanmicticEvolveScope<V, F>>() {

    override var elitism: Int = 0
        set(value) {
            require(value >= 0) { "Elitism must be positive or zero" }
            field = value
        }

    /**
     * Fills population if needed and evaluates all chromosomes (include elite ones).
     */
    public val baseBefore: suspend PanmicticEvolveScope<V, F>.() -> Unit = {
        fillPopulationIfNeed()
        evaluation(evaluateElite = true)
    }

    override var beforeEvolution: suspend PanmicticEvolveScope<V, F>.() -> Unit = baseBefore
}

/**
 * Callback before evolution process which will be invoked if [GA.iteration] is 0.
 *
 * Example:
 * ```
 * // init PanmicticGA
 * pGA(
 *     // configure population and fitnessFunction
 * ) {
 *     // set ga's configuration here
 *
 *     before { (this = PanmicticEvolveScope)
 *         println("GA STARTED, initial iteration is $iteration")
 *     }
 * }
 * ```
 * @param useDefault if true (default) used [PanmicticConfigScope.baseBefore]:
 */
public fun <V, F> PanmicticConfigScope<V, F>.before(
    useDefault: Boolean = true,
    beforeEvolution: suspend (@ConfigDslMarker PanmicticEvolveScope<V, F>).() -> Unit,
) {
    this.beforeEvolution = beforeEvolution.takeIf { !useDefault } ?: { baseBefore(); beforeEvolution() }
}

/**
 * Applies `evolutionary strategy` for [PanmicticGA] (Classical Genetic Algorithm)
 * as [evolution] function in [PanmicticEvolveScope] that includes the process of changing the population for one iteration.
 * - `evolutionary strategy` of [PanmicticGA] is applied to chromosomes within the entire population
 * and can be separated into stages: `selection`→`crossover`→`mutation`→`evaluation`.
 *
 * When [PanmicticGA] starts to evolve, its creates an `infinite loop` for your `evolutionary strategy` function.
 * There are 3 main actions that occur in this loop:
 * - Increment [GA.iteration] by one
 * - Execute [evolution]
 * - Check stops (If any stop rule is active - breaks infinite loop)
 *
 * `EXTRA INFORMATION`:
 *
 * Thus, the [evolution] function corresponds to one iteration of the genetic algorithm.
 * However, thanks to [EvolveScope], this function has the ability to change dynamically depending on various conditions,
 * which makes it easy to create unique, flexible evolution strategies, for example:
 * - create a dependence of the mutation chance on the iteration (see example)
 * - create evolutionary stages (e.g. depending on iteration)
 * that will describe different evolutionary strategies (use different genetic operators)
 * - create absolutely unique genetic operators - the entire population is at your complete disposal!
 * - calculate, compare, change!
 *
 * Example of creating base `evolutionary strategy` with [evolve] function:
 * ```
 * // init PanmicticGA
 * pGA(
 *     // configure population and fitnessFunction
 * ) {
 *     // set ga's configuration here
 *
 *     // set evolutionary strategy with evolve
 *     evolve { (this = PanmicticEvolveScope)
 *         val currentIteration = iteration
 *         selTournament(size = 3, parallelismLimit = NO_PARALLELISM)
 *         cxOnePoint(chance = 0.8)
 *         mutFlipBit(chance = 0.2, flipBitChance = 0.01 * iteration)
 *         evaluation()
 *         // Be sure to remember to set the stopping conditions
 *         stopBy(maxIteration = 50) { bestFitness == 100 }
 *     }
 * }
 * ```
 * @see before
 * @see after
 * @see stopBy
 */
public fun <V, F> PanmicticConfigScope<V, F>.evolve(
    evolution: suspend (@ConfigDslMarker PanmicticEvolveScope<V, F>).() -> Unit,
) {
    this.evolution = evolution
}

/**
 * Callback after evolution process which will be invoked when [GA.state] is going to be [State.FINISHED].
 *
 * Example:
 * ```
 * // init PanmicticGA
 * pGA(
 *     // configure population and fitnessFunction
 * ) {
 *     // set ga's configuration here
 *
 *     after { (this = PanmicticEvolveScope)
 *         println("GA is going to be FINISHED")
 *     }
 * }
 * ```
 */
public fun <V, F> PanmicticConfigScope<V, F>.after(
    afterEvolution: suspend (@ConfigDslMarker PanmicticEvolveScope<V, F>).() -> Unit,
) {
    this.afterEvolution = afterEvolution
}

/**
 * Creates [ParallelismConfig] with [ParallelismConfigScope] and apply it to the current [PanmicticConfig].
 *
 * `Panmictic Parallelism` is based on the idea of dividing [PanmicticGA] stages (`selection`, `crossover`, `mutation`, `evaluation` etc.)
 * into independent processes that can be executed in parallel mode. That's why it is called also `Genetic Operator Parallelism` -
 * parallelism is used at the level of the genetic operator, transitions between stages occur sequentially.
 *
 * There cannot be such a situation that some chromosomes go through the crossover stage, and other mutation stage -
 * the entire population can be under the influence of only one operator at one time.
 *
 * Base [selection], [crossover], [mutation], [evaluation] stage functions support `Panmictic Parallelism` by default
 * using [process] functions - it's safe to use!
 *
 * `NOTE` In most cases, the parallelism overhead for the [selection] stage exceeds its efficiency,
 * so `select operators` have parallel mode `disabled` by default.
 * Set `parallelismLimit` to non-zero value to make parallelism available.
 *
 * Example:
 * ```
 * pGA {
 *     // configure specific params of PanmicticGA
 *
 *     // configure Panmictic parallelism
 *     parallelismConfig {
 *         workersCount = 5 // max count of parallel coroutines
 *         dispatcher = Dispatchers.Default
 *     }
 *
 *     evolve {
 *         // selection stage NO_PARALLELISM by default
 *         selTournament(size = 3)
 *         // crossover stage turn off parallelism by 0 NO_PARALLELISM
 *         cxOnePoint(chance = 0.8, parallelismLimit = 0)
 *         // mutation stage parallelism available (workersCount limit)
 *         mutFlipBit(chance = 0.1, flipBitChance = 0.01)
 *         // evaluation stage parallelism also available
 *         evaluation()
 *         stopBy(maxIteration = 50) { bestFitness == 100 }
 *     }
 * }
 * ```
 * @see process
 */
public inline fun PanmicticConfigScope<*, *>.parallelismConfig(
    config: ParallelismConfigScope.() -> Unit,
): Unit = parallelismConfig(config)
