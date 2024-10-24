package kgal.panmictic

import kgal.AbstractLifecycle
import kgal.Lifecycle
import kgal.chromosome.Chromosome
import kgal.panmictic.operators.evaluation

/**
 * [PanmicticLifecycle] - specific [Lifecycle] for [PanmicticGA].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * Creates with PanmicticLifecycle().
 * @see Lifecycle
 */
public interface PanmicticLifecycle<V, F> : Lifecycle<V, F> {

    /**
     * Number of elite chromosomes - the best chromosomes of the population, which have privileged rights:
     * - recalculates on each evaluation stage and moved to start of [PanmicticPopulation]
     * - guaranteed to pass the selection stage
     * - at the crossing stage they cannot be changed or replaced,
     * but they actively participate in the creation of a new generation by changing other chromosomes
     * - do not change during the mutation stage
     * @see [PanmicticLifecycle.evaluation]
     */
    public var elitism: Int

    /**
     * Override base population as [PanmicticPopulation] for [PanmicticGA].
     */
    override val population: PanmicticPopulation<V, F>
}

/**
 * Creates an instance of [PanmicticLifecycle] by [panmicticGA] and [panmicticConfig].
 */
public fun <V, F> PanmicticLifecycle(
    panmicticGA: PanmicticGA<V, F>,
    panmicticConfig: PanmicticConfig<V, F>,
): PanmicticLifecycle<V, F> =
    PanmicticLifecycleInstance(panmicticGA, panmicticConfig)

/**
 * Base realization of [PanmicticLifecycle].
 * @see AbstractLifecycle
 */
internal class PanmicticLifecycleInstance<V, F>(
    private val panmicticGA: PanmicticGA<V, F>,
    panmicticConfig: PanmicticConfig<V, F>,
) : PanmicticLifecycle<V, F>, AbstractLifecycle<V, F>(panmicticGA, panmicticConfig) {

    override val population: PanmicticPopulation<V, F>
        get() = panmicticGA.population

    override var elitism: Int
        get() = panmicticGA.elitism
        set(value) {
            require(value >= 0) { "Elitism must be positive or zero" }
            panmicticGA.elitism = value
        }
}
