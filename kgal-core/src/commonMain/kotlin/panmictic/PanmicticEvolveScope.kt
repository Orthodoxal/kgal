package kgal.panmictic

import kgal.AbstractEvolveScope
import kgal.EvolveScope
import kgal.chromosome.Chromosome
import kgal.panmictic.operators.evaluation

/**
 * [PanmicticEvolveScope] - specific [EvolveScope] for [PanmicticGA].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * Creates with PanmicticEvolveScope().
 * @see EvolveScope
 */
public interface PanmicticEvolveScope<V, F> : EvolveScope<V, F> {

    /**
     * Number of elite chromosomes - the best chromosomes of the population, which have privileged rights:
     * - recalculates on each evaluation stage and moved to start of [PanmicticPopulation]
     * - guaranteed to pass the selection stage
     * - at the crossing stage they cannot be changed or replaced,
     * but they actively participate in the creation of a new generation by changing other chromosomes
     * - do not change during the mutation stage
     * @see [PanmicticEvolveScope.evaluation]
     */
    public var elitism: Int

    /**
     * Override base population as [PanmicticPopulation] for [PanmicticGA].
     */
    override val population: PanmicticPopulation<V, F>
}

/**
 * Creates an instance of [PanmicticEvolveScope] by [panmicticGA] and [panmicticConfig].
 */
public fun <V, F> PanmicticEvolveScope(
    panmicticGA: PanmicticGA<V, F>,
    panmicticConfig: PanmicticConfig<V, F>,
): PanmicticEvolveScope<V, F> =
    PanmicticEvolveScopeInstance(panmicticGA, panmicticConfig)

/**
 * Base realization of [PanmicticEvolveScope].
 * @see AbstractEvolveScope
 */
internal class PanmicticEvolveScopeInstance<V, F>(
    private val panmicticGA: PanmicticGA<V, F>,
    panmicticConfig: PanmicticConfig<V, F>,
) : PanmicticEvolveScope<V, F>, AbstractEvolveScope<V, F>(panmicticGA, panmicticConfig) {

    override val population: PanmicticPopulation<V, F>
        get() = panmicticGA.population

    override var elitism: Int
        get() = panmicticGA.elitism
        set(value) {
            require(value >= 0) { "Elitism must be positive or zero" }
            panmicticGA.elitism = value
        }
}
