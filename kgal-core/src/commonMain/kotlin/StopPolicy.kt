package kgal

import kotlinx.coroutines.CoroutineScope

/**
 * [StopPolicy] for [State.STOPPED] describes how [GA] must be stopped
 * @see [StopPolicy.Default]
 * @see [StopPolicy.Immediately]
 * @see [StopPolicy.Timeout]
 */
public sealed interface StopPolicy {

    /**
     * Preferred [StopPolicy].
     *
     * Waits current [GA.iteration] evolve process to be finished, stop [GA], than set [GA.state] to [State.STOPPED].
     *
     * Theoretically, it may never be completed if for some reason iteration of evolve process cannot be completed.
     *
     * For example GA with the same evolve block can never be stopped by [StopPolicy.Default]:
     *
     * ```
     * evolve {
     *     while(true) {
     *         // the endless loop will never end
     *     }
     * }
     * ```
     *
     * If you need to guarantee that the [GA] stops for your api consider using [StopPolicy.Timeout].
     */
    public data object Default : StopPolicy

    /**
     * NOT SAFE [StopPolicy] for force stop [GA] without memory leaks
     *
     * If you need to guarantee a stop, please consider using [StopPolicy.Timeout] to set a timeout to perform a safe stop by [StopPolicy.Default].
     *
     * [StopPolicy.Immediately] stop based on cancelling [CoroutineScope] of [GA] and force stop collectors of [GA.statisticsProvider],
     * than set [GA.state] to [State.STOPPED].
     *
     * This option is present to immediately stop the [GA], in case the results of the [GA] work are not relevant.
     * For example: [GA.restart]
     *
     * PLEASE AVOID USING THIS BECAUSE:
     * - Possible missed statistics data
     * - Correct resumption via [GA.resume] is not guaranteed
     */
    public data object Immediately : StopPolicy

    /**
     * [StopPolicy.Timeout] ensures that the [GA] will be stopped at least in time [millis]
     *
     * Stop [GA] with [StopPolicy.Default], after which it waits for [millis] time for stopping,
     * if the [GA] was not successfully stopped before the timeout expired, it stops with [StopPolicy.Immediately]
     *
     * [StopPolicy.Timeout] is associated with [CoroutineScope] of [GA].
     * For example: If a [GA.stop] called with [StopPolicy.Timeout],
     * and then [GA.restart] was initiated before the timeout expired,
     * the restarted [GA] will not stop by this [StopPolicy.Timeout],
     * since the stop will be considered irrelevant because [GA.restart] call create a new [CoroutineScope] of [GA].
     *
     * Please note that all calls to methods of [GA] that initiate the launch of the [GA]
     * lead to the creation of a new [CoroutineScope].
     * @see [GA.start]
     * @see [GA.resume]
     * @see [GA.restart]
     * @see [GA.stop]
     */
    public data class Timeout(val millis: Long) : StopPolicy
}
