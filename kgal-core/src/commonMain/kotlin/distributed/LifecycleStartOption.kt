package kgal.distributed

import kgal.GA
import kgal.State
import kgal.StopPolicy

/**
 * Specific [DistributedLifecycle] option for starting children of [DistributedGA].
 */
public sealed interface LifecycleStartOption {

    /**
     * Start children of [DistributedGA].
     */
    public data object Start : LifecycleStartOption

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
    ) : LifecycleStartOption

    /**
     * Resume children of [DistributedGA].
     */
    public data object Resume : LifecycleStartOption
}
