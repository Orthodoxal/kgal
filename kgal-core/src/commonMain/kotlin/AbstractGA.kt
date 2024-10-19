package kgal

import kgal.statistics.StatisticsProvider
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

/**
 * [AbstractGA] - Abstract class implementing the basic functionality of the [GA] interface.
 *
 * [L] - [AbstractGA] powered by [Lifecycle], which differs depending on the implementation.
 *
 * This class implements the work of control methods ([start], [resume], [restart], [stop]), [statisticsProvider] and control [state].
 * @param configuration base configuration for initialization [GA]
 * @see [GA]
 * @see [Config]
 * @see [Lifecycle]
 */
public abstract class AbstractGA<V, F, L : Lifecycle<V, F>>(
    configuration: Config<V, F, L>,
) : GA<V, F> {
    override var state: State = State.INITIALIZED
        protected set

    override val random: Random = configuration.random
    override var iteration: Int = 0

    override var fitnessFunction: (V) -> F = configuration.fitnessFunction

    override val statisticsProvider: StatisticsProvider by lazy {
        StatisticsProvider(name, configuration.statisticsConfig)
    }

    /**
     * Abstract lifecycle property.
     */
    protected abstract val lifecycle: L

    /**
     * Lifecycle block applied before evolution.
     */
    protected val beforeEvolution: suspend L.() -> Unit = configuration.beforeEvolution

    /**
     * Lifecycle block applied as evolution.
     */
    protected val evolution: suspend L.() -> Unit = configuration.evolution

    /**
     * Lifecycle block applied after evolution.
     */
    protected val afterEvolution: suspend L.() -> Unit = configuration.afterEvolution

    /**
     * Pause flag for [StopPolicy.Default]. If true lifecycle will stop after current iteration.
     */
    private var pause: Boolean = false

    /**
     * Main [CoroutineScope] of [GA] associated with the evolutionary process being launched.
     * Rewrites with [start], [resume], [restart].
     */
    private var coroutineScope: CoroutineScope? = null

    override suspend fun start() {
        if (state == State.STARTED) return
        startByOption(iterationFrom = 0)
    }

    override suspend fun resume() {
        if (state != State.STOPPED) return
        startByOption(iterationFrom = iteration)
    }

    override suspend fun restart(resetPopulation: Boolean) {
        if (state != State.INITIALIZED) { // equal to start() if State.INITIALIZED
            stop(stopPolicy = StopPolicy.Immediately)
            if (resetPopulation) {
                population.reset(random)
            }
        }
        startByOption(iterationFrom = 0)
    }

    override suspend fun stop(stopPolicy: StopPolicy) {
        if (state != State.STARTED && coroutineScope?.isActive == true) return

        when (stopPolicy) {
            is StopPolicy.Default -> {
                pause = true
                // wait for GA successfully stopped
                coroutineScope?.coroutineContext?.job?.join()
            }

            is StopPolicy.Immediately -> {
                coroutineScope?.cancel(
                    cause = CancellationException(
                        message = "Cluster $name stop cause force $stopPolicy policy",
                        cause = null,
                    )
                )
                statisticsProvider.stopCollectors(force = true)
                state = State.STOPPED
            }

            is StopPolicy.Timeout -> {
                val coroutineScope = coroutineScope ?: return
                pause = true
                try {
                    withTimeout(stopPolicy.millis) {
                        coroutineScope.coroutineContext.job.join()
                        val isCanceled = coroutineScope.coroutineContext.job.isCancelled
                        if (isCanceled || state != State.STARTED || !pause) return@withTimeout
                        stop(stopPolicy = StopPolicy.Immediately)
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
    protected open suspend fun startByOption(iterationFrom: Int) {
        statisticsProvider.prepareStatistics()
        this.iteration = iterationFrom

        coroutineScope {
            coroutineScope = this
            launch {
                launch()
                coroutineScope = null
            }
        }
    }

    /**
     * Launch [GA] with [lifecycle].
     */
    protected suspend fun launch() {
        with(lifecycle) {
            beforeEvolve()
            evolve()
            afterEvolve()
        }
    }

    /**
     * Prepare [lifecycle] to start. Executes [beforeEvolution] if necessary.
     */
    protected open suspend fun L.beforeEvolve() {
        pause = false
        finishByStopConditions = false
        finishedByMaxIteration = false

        if (iteration == 0) {
            beforeEvolution()
        }
    }

    /**
     * Start infinite evolutionary loop with stop checks on each iteration.
     */
    protected open suspend fun L.evolve() {
        state = State.STARTED
        while (true) {
            this@AbstractGA.iteration++
            evolution()

            if (finishByStopConditions) {
                state = State.FINISHED.ByStopConditions
                break
            }

            if (finishedByMaxIteration) {
                state = State.FINISHED.ByMaxIteration
                break
            }

            if (pause) {
                state = State.STOPPED
                break
            }
        }
    }

    /**
     * Prepare [lifecycle] to stop or finish. Executes [afterEvolution] if necessary.
     */
    protected open suspend fun L.afterEvolve() {
        // wait for all children coroutines of lifecycle completed
        coroutineContext.job.children.forEach { it.join() }
        // stop all statistics collectors and wait for all data has been handled (force = false)
        statisticsProvider.stopCollectors(force = false)

        if (state is State.FINISHED) {
            afterEvolution()
        }
    }
}
