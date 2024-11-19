package kgal.statistics

import kgal.GA
import kgal.State
import kotlin.jvm.JvmInline
import kotlin.time.Duration

/**
 * [Session] describes activity of [GA] from first [State.STARTED] state to [State.FINISHED].
 *
 * The [GA] has as many sessions as there are completions (FINISHED):
 * ```
 * Session 1 started:
 * STARTED
 * STOPPED
 * STARTED
 * FINISHED
 *
 * Session 2 started:
 * STARTED
 * FINISHED
 * ```
 * @param id unique identical number for session
 * @param logNotes notes for describing session activity
 * @see SessionsInfo
 * @see allSessions
 */
public data class Session(
    val id: Int,
    val logNotes: List<LogNote>,
)

/**
 * A session note linked to a [State] for describing activity of [GA] per [Session].
 */
public sealed interface LogNote {

    /**
     * [GA] has been launched with [GA.start], [GA.resume] or [GA.restart].
     */
    public data object STARTED : LogNote

    /**
     * [GA] has been stopped (Includes iterations for subsession).
     * @param iterations number of iterations per subsession: STARTED -> STOPPED
     * @param duration durations of per subsession: STARTED -> STOPPED
     */
    public data class STOPPED(
        val iterations: Int,
        val duration: Duration,
    ) : LogNote

    /**
     * [GA] has been finished (Includes iterations for subsession).
     * @param iterations number of iterations per subsession: FINISHED -> STOPPED
     * @param duration durations of per subsession: FINISHED -> STOPPED
     */
    public data class FINISHED(
        val iterations: Int,
        val duration: Duration,
    ) : LogNote
}

/**
 * [SessionsInfo] helper container for collecting [Session] of [GA].
 */
@JvmInline
public value class SessionsInfo(
    public val sessions: List<Session>,
) {

    /**
     * Prints to console session information.
     */
    public fun print(): Unit = println(this)

    override fun toString(): String = buildString {
        for (session in sessions) {
            append("\nSession ${session.id} initialized:\n")
            for (logNote in session.logNotes) {
                when (logNote) {
                    LogNote.STARTED -> append("STARTED\n")
                    is LogNote.STOPPED -> append("STOPPED (${logNote.iterations} iterations executed): ${logNote.duration} \n")
                    is LogNote.FINISHED -> append("FINISHED (${logNote.iterations} iterations executed): ${logNote.duration} \n")
                }
            }
        }
    }
}

/**
 * Creates [SessionsInfo] that describes all activity of [GA].
 * @see [SessionsInfo]
 * @see [Session]
 * @see [LogNote]
 */
public val GA<*, *>.allSessions: SessionsInfo
    get() {
        with(timeStore) {
            var started: TimeMarker? = onStarted.getOrNull(0) ?: return SessionsInfo(sessions = emptyList())
            var startedIndex = 0
            var finishedIndex = 0
            var iterationIndex = 0
            var stoppedIndex = 0

            var finished = onFinished.getOrNull(finishedIndex)
            var iteration = onIteration.getOrNull(iterationIndex)
            var stopped = onStopped.getOrNull(stoppedIndex)
            var iterationsPerSubsession = -1
            val logNotes: MutableList<LogNote> = mutableListOf()
            val sessions: MutableList<Session> = mutableListOf()
            var sessionId = 1

            while (started != null) {
                logNotes.add(LogNote.STARTED)
                val startedNext = onStarted.getOrNull(++startedIndex)

                while (finished lessOrEqual startedNext || iteration lessOrEqual startedNext || stopped lessOrEqual startedNext) {

                    when {
                        iteration more stopped && stopped lessOrEqual finished -> {
                            logNotes.add(
                                LogNote.STOPPED(
                                    iterations = iterationsPerSubsession,
                                    duration = stopped!! - started
                                )
                            )
                            iterationsPerSubsession = -1
                            stopped = onStopped.getOrNull(++stoppedIndex)
                        }

                        iteration more finished -> {
                            logNotes.add(
                                LogNote.FINISHED(
                                    iterations = iterationsPerSubsession,
                                    duration = finished!! - started
                                )
                            )
                            sessions.add(Session(id = sessionId++, logNotes = logNotes.toList()))
                            logNotes.clear()
                            iterationsPerSubsession = -1
                            finished = onFinished.getOrNull(++finishedIndex)
                        }

                        else -> {
                            iteration?.let {
                                ++iterationsPerSubsession
                            }
                            iteration = onIteration.getOrNull(++iterationIndex)
                        }
                    }
                }
                started = startedNext
            }
            return SessionsInfo(sessions)
        }
    }

/**
 * Return `true` if [this] more than [other].
 *
 * Assumes `null` will be in the future:
 * - `null` > `not null` is `true`
 * - `not null` > `null` is `false`
 * - `null` > `null` is `false` (future can not be more than future)
 */
internal infix fun TimeMarker?.more(other: TimeMarker?): Boolean {
    if (this == null) return other != null // if other not null -> this (in future) > other
    if (other == null) return false // if other is null -> this < other (in future)
    return this > other
}

/**
 * Return `true` if [this] less or equal to [other].
 *
 * Assumes `null` will be in the future:
 * - `null` <= `not null` is `false`
 * - `not null` <= `null` is `true`
 * - `null` <= `null` is `true`
 */
internal infix fun TimeMarker?.lessOrEqual(other: TimeMarker?): Boolean {
    if (this == null) return false
    if (other == null) return true
    return this <= other
}
