package kgal

import kgal.panmictic.PanmicticConfig
import kgal.processor.parallelism.ParallelismConfig
import kgal.statistics.StatisticsConfig
import kgal.statistics.TimeStore
import kgal.statistics.TimeMarker
import kotlin.random.Random

/**
 * Base configuration interface for [GA]
 * Describes the configuration parameters necessary for the operation of the [GA].
 * @see AbstractConfigScope
 * @see PanmicticConfig
 */
public interface Config<V, F, L : Lifecycle<V, F>> {

    /**
     * Random associated with [GA]. Defines a pseudorandom number generator for predictive calculations.
     */
    public var random: Random

    /**
     * Population configuration for genetic algorithm.
     */
    public val population: Population<V, F>

    /**
     * Fitness function -
     * A function that evaluates the quality or "fitness" of each individual (chromosome) in a population.
     * The fitness function determines how well a particular solution matches the target problem.
     */
    public val fitnessFunction: (V) -> F

    /**
     * Statistics configuration associated with [GA].
     */
    public val statisticsConfig: StatisticsConfig

    /**
     * Parallelism configuration associated with [GA].
     */
    public val parallelismConfig: ParallelismConfig

    /**
     *  Store for all [TimeMarker]s of [GA].
     */
    public val timeStore: TimeStore

    /**
     * Callback before evolution process. Executed only once at launch if [GA.iteration] is 0.
     */
    public val beforeEvolution: suspend L.() -> Unit

    /**
     * Main evolution process.
     */
    public val evolution: suspend L.() -> Unit

    /**
     * Callback after evolution process. Executed at launch when [GA.state] is going to be [State.FINISHED].
     */
    public val afterEvolution: suspend L.() -> Unit
}
