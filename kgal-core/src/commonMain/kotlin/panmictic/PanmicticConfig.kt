package kgal.panmictic

import kgal.AbstractConfigScope
import kgal.Config
import kgal.operators.fillPopulationIfNeed
import kgal.panmictic.operators.evaluation.evaluation
import kgal.panmictic.operators.selection.recalculateEliteChromosomes

/**
 * Describes the configuration parameters necessary for the operation of the [PanmicticGA].
 * @see PanmicticConfigScope
 */
public interface PanmicticConfig<V, F> : Config<V, F, PanmicticLifecycle<V, F>> {

    /**
     * Number of elite chromosomes - the best chromosomes of the population, which have privileged rights:
     * - Before each selection stage, the values are recalculated and moved to start of [PanmicticPopulation]
     * - Guaranteed to pass the selection stage
     * - At the crossing stage they cannot be changed or replaced,
     * but they actively participate in the creation of a new generation by changing other chromosomes
     * - Do not change during the mutation stage
     * @see [recalculateEliteChromosomes]
     */
    public val elitism: Int

    /**
     * Override base [population] as [PanmicticPopulation] for [PanmicticGA].
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
) : PanmicticConfig<V, F>, AbstractConfigScope<V, F, PanmicticLifecycle<V, F>>() {

    override var elitism: Int = 0

    override val baseBefore: suspend PanmicticLifecycle<V, F>.() -> Unit = { fillPopulationIfNeed(); evaluation() }
    override var beforeEvolution: suspend PanmicticLifecycle<V, F>.() -> Unit = baseBefore
}
