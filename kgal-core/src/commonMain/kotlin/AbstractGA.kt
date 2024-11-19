package kgal

import kgal.statistics.StatisticsProvider
import kgal.statistics.TimeStore
import kgal.statistics.timeMarker
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlin.coroutines.coroutineContext
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * [AbstractGA] - Abstract class implementing the basic functionality of the [GA] interface.
 *
 * [ES] - [AbstractGA] powered by [EvolveScope], which differs depending on the implementation.
 *
 * This class implements the work of control methods ([start], [resume], [restart], [stop]), [statisticsProvider] and control [state].
 * @param configuration base configuration for initialization [GA]
 * @see [GA]
 * @see [Config]
 * @see [EvolveScope]
 */
public abstract class AbstractGA<V, F, ES : EvolveScope<V, F>>(
    configuration: Config<V, F, ES>,
) : GA<V, F> {
    override var state: State = State.INITIALIZED
        protected set

    override val isActive: Boolean
        get() = processingMutex.isLocked

    override val random: Random = configuration.random
    override var iteration: Int = 0

    override var fitnessFunction: (V) -> F = configuration.fitnessFunction

    override val statisticsProvider: StatisticsProvider by lazy {
        StatisticsProvider(name, configuration.statisticsConfig)
    }

    override val timeStore: TimeStore = configuration.timeStore

    /**
     * Abstract evolveScope property.
     */
    protected abstract val evolveScope: ES

    /**
     * EvolveScope block applied before evolution.
     */
    protected val beforeEvolution: suspend ES.() -> Unit = configuration.beforeEvolution

    /**
     * EvolveScope block applied as evolution.
     */
    protected val evolution: suspend ES.() -> Unit = configuration.evolution

    /**
     * EvolveScope block applied after evolution.
     */
    protected val afterEvolution: suspend ES.() -> Unit = configuration.afterEvolution

    /**
     * Pause flag for [StopPolicy.Default]. If true evolution will be stopped after current iteration.
     */
    protected var pause: Boolean = false

    /**
     * Main [CoroutineScope] of [GA] associated with the evolutionary process being launched.
     * Rewrites with [start], [resume], [restart].
     */
    protected var coroutineScope: CoroutineScope? = null

    /**
     * Processing mutex of evolution (only one [coroutineScope] can be executed in any moment).
     */
    protected val processingMutex: Mutex = Mutex()

    override suspend fun start() {
        startByOption(iterationFrom = 0)
    }

    override suspend fun resume() {
        if (isActive || state is State.FINISHED) return
        startByOption(iterationFrom = iteration)
    }

    override suspend fun restart(
        forceStop: Boolean,
        resetPopulation: Boolean,
    ) {
        if (state != State.INITIALIZED) { // equal to start() if State.INITIALIZED
            if (forceStop) {
                stop(stopPolicy = StopPolicy.Immediately)
            } else if (state !is State.FINISHED) {
                stop(stopPolicy = StopPolicy.Default)
            }

            evolveScope.store.clear()

            if (resetPopulation) {
                population.reset(random)
            }
        }
        startByOption(iterationFrom = 0)
    }

    override suspend fun stop(stopPolicy: StopPolicy) {
        if (!isActive) return

        when (stopPolicy) {
            is StopPolicy.Default -> {
                pause = true
                coroutineScope?.coroutineContext?.job?.join() // wait for GA successfully stopped
            }

            is StopPolicy.Immediately -> {
                coroutineScope?.cancel(
                    cause = CancellationException(
                        message = "Cluster $name stop cause force $stopPolicy policy",
                        cause = null,
                    )
                )
                pause = true
                statisticsProvider.stopCollectors(force = true)
            }

            is StopPolicy.Timeout -> {
                try {
                    withTimeout(stopPolicy.millis) {
                        stop(stopPolicy = StopPolicy.Default)
                    }
                } catch (_: TimeoutCancellationException) {
                    stop(stopPolicy = StopPolicy.Immediately)
                }
            }
        }
    }

    /**
     * Prepares GA for launch. Creates [coroutineScope] for evolve process [GA].
     * @param iterationFrom set current iteration
     */
    @OptIn(ExperimentalUuidApi::class)
    protected open suspend fun startByOption(iterationFrom: Int) {
        val sessionToken: Uuid = Uuid.random()
        if (!processingMutex.tryLock(owner = sessionToken)) {
            throw IllegalStateException("Attempting to launch an active GA. GA can only work in one coroutineScope at a time.")
        }

        try {
            statisticsProvider.prepareStatistics()
            this.iteration = iterationFrom

            coroutineScope {
                coroutineScope = this
                launch {
                    launch()
                    coroutineScope = null
                }
            }
        } finally {
            if (state !is State.FINISHED) { // GA IS STOPPED
                state = State.STOPPED
                timeStore.onStopped.add(timeMarker)
            }
            processingMutex.unlock(sessionToken)
        }
    }

    /**
     * Launches [GA] with [evolveScope].
     */
    protected suspend fun launch() {
        with(evolveScope) {
            beforeEvolve()
            evolve()
            afterEvolve()
        }
    }

    /**
     * Prepare [evolveScope] to start. Executes [beforeEvolution] if necessary.
     */
    protected open suspend fun ES.beforeEvolve() {
        // prepare GA and EvolveScope
        pause = false
        finishByStopConditions = false
        finishedByMaxIteration = false

        state = State.STARTED
        timeStore.onStarted.add(timeMarker)
        timeStore.onIteration.add(timeMarker)

        if (iteration == 0) {
            beforeEvolution()
        }
    }

    /**
     * Start infinite evolutionary loop with stop checks on each iteration.
     */
    protected open suspend fun ES.evolve() {
        while (true) {
            this@AbstractGA.iteration++
            evolution()

            // Loop break conditions
            if (finishByStopConditions || finishedByMaxIteration || pause) {
                break
            }
        }
    }

    /**
     * Prepare [evolveScope] to stop or finish. Executes [afterEvolution] if necessary.
     */
    protected open suspend fun ES.afterEvolve() {
        // wait for all children coroutines of coroutineScope completed
        coroutineContext.job.children.forEach { it.join() }
        // stop all statistics collectors and wait for all data has been handled (force = false)
        statisticsProvider.stopCollectors(force = false)

        val newState = when {
            finishByStopConditions -> State.FINISHED.ByStopConditions
            finishedByMaxIteration -> State.FINISHED.ByMaxIteration
            else -> state
        }

        if (newState is State.FINISHED) {
            timeStore.onFinished.add(timeMarker)
            afterEvolution()
            store.clear()
            state = newState
        }
    }
}
