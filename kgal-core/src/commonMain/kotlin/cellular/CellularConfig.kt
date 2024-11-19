package kgal.cellular

import kgal.AbstractConfigScope
import kgal.Config
import kgal.GA
import kgal.State
import kgal.cellular.neighborhood.Moore
import kgal.cellular.operators.*
import kgal.dsl.ConfigDslMarker
import kgal.operators.stopBy
import kgal.panmictic.PanmicticGA
import kgal.parallelismConfig
import kgal.processor.parallelism.ParallelismConfig
import kgal.processor.parallelism.ParallelismConfigScope
import kgal.processor.process

/**
 * Describes the configuration parameters necessary for the operation of the [CellularGA].
 * @see CellularConfigScope
 */
public interface CellularConfig<V, F> : Config<V, F, CellularLifecycle<V, F>> {

    /**
     * Flag for elitism in [CellularGA].
     *
     * If `true` parent chromosome will be replaced with child only if `child.fitness > parent.fitness`.
     * @see replaceWithElitism
     */
    public val elitism: Boolean

    /**
     * Cellular type for [CellularGA].
     * @see CellularType
     */
    public val cellularType: CellularType

    /**
     * Neighborhood for [CellularGA].
     * @see [CellularNeighborhood]
     */
    public val neighborhood: CellularNeighborhood

    /**
     * Override base population as [CellularPopulation] for [CellularGA].
     */
    public override val population: CellularPopulation<V, F>
}

/**
 * Implementation of [CellularConfig] based on [AbstractConfigScope].
 * Params [population] and [fitnessFunction] are considered mandatory.
 */
public class CellularConfigScope<V, F>(
    override val population: CellularPopulation<V, F>,
    override var fitnessFunction: (V) -> F,
) : CellularConfig<V, F>, AbstractConfigScope<V, F, CellularLifecycle<V, F>>() {

    override var elitism: Boolean = true

    override var cellularType: CellularType = CellularType.Synchronous

    override var neighborhood: CellularNeighborhood = Moore(radius = 1)

    /**
     * Fills population if needed, cached neighborhood indices and evaluates all chromosomes.
     */
    public val baseBefore: suspend CellularLifecycle<V, F>.() -> Unit = {
        fillPopulationIfNeed()
        cacheNeighborhood()
        evaluationAll()
    }

    override var beforeEvolution: suspend CellularLifecycle<V, F>.() -> Unit = baseBefore
}

/**
 * Callback before evolution process which will be invoked if [GA.iteration] is 0.
 *
 * Example:
 * ```
 * // init CellularGA
 * cGA(
 *     // configure population and fitnessFunction
 * ) {
 *     // set ga's configuration here
 *
 *     before { (this = CellularLifecycle)
 *         println("GA STARTED, initial iteration is $iteration")
 *     }
 * }
 * ```
 * @param useDefault if true (default) used [CellularConfigScope.baseBefore]:
 */
public fun <V, F> CellularConfigScope<V, F>.before(
    useDefault: Boolean = true,
    beforeEvolution: suspend (@ConfigDslMarker CellularLifecycle<V, F>).() -> Unit,
) {
    this.beforeEvolution = beforeEvolution.takeIf { !useDefault } ?: { baseBefore(); beforeEvolution() }
}

/**
 * Applies `evolutionary strategy` for [CellularGA] (Cellular Genetic Algorithm)
 * as [evolution] function in [CellularLifecycle] scope that includes the process of changing the population for one iteration.
 *
 * - `evolutionary strategy` of [CellularGA] is to separate chromosomes and their neighborhoods into N `cell evolutionary strategies`,
 * where N - usually equal to [CellularPopulation.size].
 * Each of these strategies is applied to the corresponding `cell` chromosome within their `neighborhood`
 * and can be separated into stages: `selection`→`crossover`→`mutation`→`evaluation` like `evolutionary strategy` of [PanmicticGA].
 *
 * Example for Von Neumann neighborhood with `radius = 1`:
 * ```
 * X   X   X   X   X
 * X   X   N   X   X
 * X   N   C   N   X
 * X   X   N   X   X
 * X   X   X   X   X
 * ```
 * Where `C` - target cell chromosome, `N` - neighbors for current target chromosomes, `X` - other chromosomes in population.
 * `cell evolutionary strategy` is to obtain the child chromosome from `C` chromosome (first parent) and second parent (can be only chosen among `N` chromosomes),
 * the resulting child can replace `C` chromosome in population.
 *
 * - To create an `cell evolutionary strategy`, declare the [evolveCells] operator inside the [evolve] function
 * and specify the evolution function in scope [CellLifecycle] for [CellLifecycle.cell] and its [CellLifecycle.neighbors].
 * [evolveCells] operator will execute [CellularPopulation.size] cell-evolutions what will create a new generation, see example below:
 * ```
 * // init CellularGA
 * cGA(
 *     // configure population and fitnessFunction
 * ) {
 *     // set ga's configuration here
 *
 *     evolve {
 *         // actual population here
 *         evolveCells {
 *             selTournament(size = 3)
 *             cxOnePoint(chance = 0.9)
 *             mutFlipBit(chance = 0.2, flipBitChance = 0.02)
 *             evaluation()
 *         }
 *         // offspring here (new generation)
 *
 *         println("Iteration $iteration: best fitness = $bestFitness")
 *         stopBy(maxIteration = 50) { bestFitness == 100 }
 *     }
 * }
 * ```
 * Where `C` - target chromosome, `N` - neighbors for current target chromosomes, `X` - other chromosomes in population.
 * @see CellLifecycle
 * @see evolveCells
 * @see before
 * @see after
 * @see stopBy
 */
public fun <V, F> CellularConfigScope<V, F>.evolve(
    evolution: suspend (@ConfigDslMarker CellularLifecycle<V, F>).() -> Unit,
) {
    this.evolution = evolution
}

/**
 * Callback after evolution process which will be invoked when [GA.state] is going to be [State.FINISHED].
 *
 * Example:
 * ```
 * // init CellularGA
 * cGA(
 *     // configure population and fitnessFunction
 * ) {
 *     // set ga's configuration here
 *
 *     after { (this = CellularLifecycle)
 *         println("GA is going to be FINISHED")
 *     }
 * }
 * ```
 */
public fun <V, F> CellularConfigScope<V, F>.after(
    afterEvolution: suspend (@ConfigDslMarker CellularLifecycle<V, F>).() -> Unit,
) {
    this.afterEvolution = afterEvolution
}

/**
 * Creates [ParallelismConfig] with [ParallelismConfigScope] and apply it to the current [CellularConfig].
 *
 * `Cellular Parallelism` is based on the idea of dividing `cell evolutionary strategies`
 * into independent processes that can be executed in parallel mode.
 * This type of parallelism is more efficient than `Panmictic Parallelism`.
 *
 * Base [evolveCells] stage function support `Cellular Parallelism` by default
 * using [process] functions with [ParallelismConfig.workersCount] limit - it's safe to use!
 *
 * `NOTE` results for [CellularType.Asynchronous] in parallel mode may be `unpredictable` (even a randomSeed set will not guarantee a repeatable result)
 * since child chromosomes may replace parents at different rates depending on the operation of independent coroutines.
 * However, this also introduces an element of randomness to evolution, which can have a positive effect on the entire process.
 * [CellularType.Synchronous] is `safe` for parallelism because parallel evolutionary processes occur on an unchangeable parent population.
 *
 * Example:
 * ```
 * cGA {
 *     // configure specific params of CellularGA
 *
 *     // configure Cellular parallelism
 *     parallelismConfig {
 *         workersCount = 5 // max count of parallel coroutines
 *         dispatcher = Dispatchers.Default
 *     }
 *
 *     evolve {
 *         // evolving cells stage parallelism available
 *         // ParallelismConfig.workersCount limit by default
 *         evolveCells {
 *             selTournament(size = 3)
 *             cxOnePoint(chance = 0.8)
 *             mutFlipBit(chance = 0.1, flipBitChance = 0.01)
 *             evaluation()
 *         }
 *
 *         stopBy(maxIteration = 50) { bestFitness == 100 }
 *     }
 * }
 * ```
 * @see process
 */
public inline fun CellularConfigScope<*, *>.parallelismConfig(
    config: ParallelismConfigScope.() -> Unit,
): Unit = parallelismConfig(config)
