package kgal.distributed

import kgal.AbstractEvolveScope
import kgal.EvolveScope
import kgal.GA
import kgal.chromosome.Chromosome
import kgal.distributed.operators.launchChildren

/**
 * [DistributedEvolveScope] - specific [EvolveScope] for [DistributedGA].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * Creates with DistributedEvolveScope().
 * @see EvolveScope
 */
public interface DistributedEvolveScope<V, F> : EvolveScope<V, F> {

    /**
     * Option for starting [children].
     * @see launchChildren
     */
    public var startOption: EvolveScopeStartOption

    /**
     * Child GAs for owner [DistributedGA].
     */
    public val children: List<GA<V, F>>

    /**
     * Override base population as [DistributedPopulation] for [DistributedGA].
     */
    public override val population: DistributedPopulation<V, F>
}

/**
 * Creates an instance of [DistributedEvolveScope] by [distributedGA] and [distributedConfig].
 */
public fun <V, F> DistributedEvolveScope(
    distributedGA: DistributedGA<V, F>,
    distributedConfig: DistributedConfig<V, F>,
): DistributedEvolveScope<V, F> =
    DistributedEvolveScopeInstance(distributedGA, distributedConfig)

/**
 * Base realization of [DistributedEvolveScope].
 * @see AbstractEvolveScope
 */
internal class DistributedEvolveScopeInstance<V, F>(
    private val distributedGA: DistributedGA<V, F>,
    distributedConfig: DistributedConfig<V, F>,
) : DistributedEvolveScope<V, F>, AbstractEvolveScope<V, F>(distributedGA, distributedConfig) {

    override var startOption: EvolveScopeStartOption = EvolveScopeStartOption.Start

    override val population: DistributedPopulation<V, F>
        get() = distributedGA.population

    override val children: List<GA<V, F>>
        get() = distributedGA.children
}
