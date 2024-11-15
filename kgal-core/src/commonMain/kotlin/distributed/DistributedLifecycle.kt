package kgal.distributed

import kgal.AbstractLifecycle
import kgal.GA
import kgal.Lifecycle
import kgal.chromosome.Chromosome
import kgal.distributed.operators.launchChildren

/**
 * [DistributedLifecycle] - specific [Lifecycle] for [DistributedGA].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * Creates with DistributedLifecycle().
 * @see Lifecycle
 */
public interface DistributedLifecycle<V, F> : Lifecycle<V, F> {

    /**
     * Option for starting [children].
     * @see launchChildren
     */
    public var startOption: LifecycleStartOption

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
 * Creates an instance of [DistributedLifecycle] by [distributedGA] and [distributedConfig].
 */
public fun <V, F> DistributedLifecycle(
    distributedGA: DistributedGA<V, F>,
    distributedConfig: DistributedConfig<V, F>,
): DistributedLifecycle<V, F> =
    DistributedLifecycleInstance(distributedGA, distributedConfig)

/**
 * Base realization of [DistributedLifecycle].
 * @see AbstractLifecycle
 */
internal class DistributedLifecycleInstance<V, F>(
    private val distributedGA: DistributedGA<V, F>,
    distributedConfig: DistributedConfig<V, F>,
) : DistributedLifecycle<V, F>, AbstractLifecycle<V, F>(distributedGA, distributedConfig) {

    override var startOption: LifecycleStartOption = LifecycleStartOption.Start

    override val population: DistributedPopulation<V, F>
        get() = distributedGA.population

    override val children: List<GA<V, F>>
        get() = distributedGA.children
}
