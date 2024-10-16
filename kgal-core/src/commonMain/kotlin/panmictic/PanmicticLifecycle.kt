package kgal.panmictic

import kgal.AbstractLifecycle
import kgal.Lifecycle
import kgal.chromosome.Chromosome
import kgal.panmictic.operators.selection.recalculateEliteChromosomes
import kgal.processor.parallelism.ParallelismConfig

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
     * - Before each selection stage, the values are recalculated and moved to start of [PanmicticPopulation]
     * - Guaranteed to pass the selection stage
     * - At the crossing stage they cannot be changed or replaced,
     * but they actively participate in the creation of a new generation by changing other chromosomes
     * - Do not change during the mutation stage
     * @see [recalculateEliteChromosomes]
     */
    public var elitism: Int

    /**
     * Override base [population] as [PanmicticPopulation] for [PanmicticGA].
     */
    override val population: PanmicticPopulation<V, F>
}

/**
 * Creates an instance of [PanmicticLifecycle] by [panmicticGA] and [parallelismConfig].
 */
public fun <V, F> PanmicticLifecycle(
    panmicticGA: PanmicticGA<V, F>,
    parallelismConfig: ParallelismConfig,
): PanmicticLifecycle<V, F> =
    PanmicticLifecycleInstance(panmicticGA, parallelismConfig)

/**
 * Base realization of [PanmicticLifecycle].
 * @see AbstractLifecycle
 */
internal class PanmicticLifecycleInstance<V, F>(
    private val panmicticGA: PanmicticGA<V, F>,
    override val parallelismConfig: ParallelismConfig,
) : PanmicticLifecycle<V, F>, AbstractLifecycle<V, F>(panmicticGA) {

    override val population: PanmicticPopulation<V, F>
        get() = panmicticGA.population

    override var elitism: Int
        get() = panmicticGA.elitism
        set(value) {
            panmicticGA.elitism = value
        }
}
