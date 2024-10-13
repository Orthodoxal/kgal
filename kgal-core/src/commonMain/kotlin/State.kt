package kgal

/**
 * This [State] describes the state of a genetic algorithm
 */
public sealed interface State {

    /**
     * [GA] has been initialized
     *
     * Ready to start
     */
    public data object INITIALIZED : State

    /**
     * [GA] has been started and works in current moment
     *
     * Ready to stop any moment
     */
    public data object STARTED : State

    /**
     * [GA] has been stopped with [StopPolicy]
     *
     * Ready to:
     * - resume (continue with previous [GA.iteration])
     * - start (continue with the same population and reset [GA.iteration] to 0)
     * - restart (continue with the same or new population and reset [GA.iteration] to 0)
     * @see StopPolicy
     */
    public data object STOPPED : State

    /**
     * [GA] has been finished works
     * @see [FINISHED.ByMaxIteration]
     * @see [FINISHED.ByStopConditions]
     */
    public sealed interface FINISHED : State {
        /**
         * [GA] has been finished by limit for max iteration
         */
        public data object ByMaxIteration : FINISHED

        /**
         * [GA] was finished by one of any stopping condition rules
         */
        public data object ByStopConditions : FINISHED
    }
}
