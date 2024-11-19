package kgal.distributed

import kgal.GA
import kgal.State
import kgal.StopPolicy

/**
 * Specific [DistributedEvolveScope] option for starting children of [DistributedGA].
 */
public sealed interface EvolveScopeStartOption {

    /**
     * Start children of [DistributedGA].
     */
    public data object Start : EvolveScopeStartOption

    /**
     * Immediately restart children of [DistributedGA].
     *
     * Equals to [Start] if [GA.state] is [State.INITIALIZED].
     * @param forceStop - if `true` and [GA] is active stop [GA] with [StopPolicy.Immediately] else [StopPolicy.Default]
     * @param resetPopulation if `true` all progress will be lost.
     */
    public data class Restart(
        val forceStop: Boolean = false,
        val resetPopulation: Boolean = true,
    ) : EvolveScopeStartOption

    /**
     * Resume children of [DistributedGA].
     */
    public data object Resume : EvolveScopeStartOption
}
