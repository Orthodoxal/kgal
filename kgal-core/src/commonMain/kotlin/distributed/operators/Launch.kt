package kgal.distributed.operators

import kgal.GA
import kgal.State
import kgal.distributed.DistributedEvolveScope
import kgal.distributed.DistributedGA
import kgal.distributed.EvolveScopeStartOption
import kgal.processor.process

/**
 * Returns `true` if any [GA] in list has [State.FINISHED.ByStopConditions] state.
 */
private val List<GA<*, *>>.anyFinishedByStopConditions
    get() = any { it.state is State.FINISHED.ByStopConditions }

/**
 * Executes synchronized launch of [DistributedEvolveScope.children] with [DistributedEvolveScope.startOption].
 * 1) launch children (include parallel mode)
 * 2) waits all to be finished with [process]
 * 3) if any child GA is finished by [State.FINISHED.ByStopConditions],
 * set [DistributedEvolveScope.finishByStopConditions] to `true` (mark this iteration as last for [DistributedGA])
 *
 * @param parallelismLimit limit of parallel workers
 */
public suspend fun <V, F> DistributedEvolveScope<V, F>.launchChildren(parallelismLimit: Int) {
    process(
        parallelismLimit = parallelismLimit,
        startIteration = 0,
        endIteration = children.size,
        action = when (val startOption = startOption) {
            EvolveScopeStartOption.Start -> { iteration, _ -> children[iteration].start() }
            EvolveScopeStartOption.Resume -> { iteration, _ -> children[iteration].resume() }
            is EvolveScopeStartOption.Restart -> { iteration, _ ->
                children[iteration].restart(
                    forceStop = startOption.forceStop,
                    resetPopulation = startOption.resetPopulation,
                )
            }
        }
    )

    startOption = EvolveScopeStartOption.Start
    if (children.anyFinishedByStopConditions) {
        finishByStopConditions = true
    }
}
