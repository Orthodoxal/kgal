package kgal.statistics.note

import kgal.GA
import kgal.Lifecycle
import kgal.name
import kotlin.jvm.JvmName

/**
 * [SingleStatisticNote] - single record statistics
 * @see StatisticNote
 */
public data class SingleStatisticNote<V>(
    override val statistic: Statistic<V>,
    override val ownerName: String,
    override val iteration: Int,
) : StatisticNote<V> {
    override fun toString(): String = "$ownerName\t $iteration\t ${statistic.name}\t ${statistic.value}"
}

/**
 * Creates [SingleStatisticNote] and send it to [GA.statisticsProvider]
 *
 * Example:
 * ```
 * evolution {
 *     stat(name = "BEST", value = best)
 * }
 *
 * ```
 * @param name - Name of statistics
 * @param value - Value of statistics
 * @see SingleStatisticNote
 */
public suspend fun Lifecycle<*, *>.stat(
    name: String,
    value: Any?,
): Unit = emitStat(
    SingleStatisticNote(
        statistic = Statistic(name, value),
        ownerName = this.name,
        iteration = this.iteration,
    )
)

/**
 * Creates [SingleStatisticNote] and send it to [GA.statisticsProvider]
 *
 * Example:
 * ```
 * evolution {
 *     stat("BEST" to best)
 * }
 *
 * ```
 * @see SingleStatisticNote
 */
@JvmName("statValue")
public suspend fun Lifecycle<*, *>.stat(
    pair: Pair<String, Any?>,
): Unit = emitStat(
    SingleStatisticNote(
        statistic = Statistic(pair.first, pair.second),
        ownerName = this.name,
        iteration = this.iteration,
    )
)

/**
 * Creates [SingleStatisticNote] and send it to [GA.statisticsProvider]
 * Use [registrar] function
 *
 * Example:
 * ```
 * evolution {
 *     stat(
 *         name = "CUSTOM",
 *         registrar = {
 *             val res1 = function1(param1 = best, param2 = worst)
 *             val res2 = function2(param1 = best, param2 = worst)
 *             res1 + res2
 *         },
 *     )
 * }
 *
 * ```
 * @param name - Name of statistics
 * @see SingleStatisticNote
 */
public suspend inline fun Lifecycle<*, *>.stat(
    name: String,
    registrar: () -> Any?,
): Unit = emitStat(
    SingleStatisticNote(
        statistic = Statistic(name, registrar()),
        ownerName = this.name,
        iteration = this.iteration,
    )
)

/**
 * Creates [SingleStatisticNote] and send it to [GA.statisticsProvider]
 *
 * Example:
 * ```
 * evolution {
 *     stat(
 *         "CUSTOM" to {
 *            val res1 = function1(param1 = best, param2 = worst)
 *            val res2 = function2(param1 = best, param2 = worst)
 *            res1 + res2
 *         }
 *     )
 * }
 *
 * ```
 * @see SingleStatisticNote
 */
@JvmName("statRegister")
public suspend fun Lifecycle<*, *>.stat(
    pair: Pair<String, () -> Any?>,
): Unit = emitStat(
    SingleStatisticNote(
        statistic = Statistic(pair.first, pair.second()),
        ownerName = this.name,
        iteration = this.iteration,
    )
)

/**
 * Creates [SingleStatisticNote] and send it to [GA.statisticsProvider]
 *
 * Example:
 * ```
 * evolution {
 *     stat(best())
 * }
 *
 * ```
 * @see SingleStatisticNote
 */
public suspend fun Lifecycle<*, *>.stat(
    statistic: Statistic<Any?>,
): Unit = emitStat(
    SingleStatisticNote(
        statistic = statistic,
        ownerName = this.name,
        iteration = this.iteration,
    )
)
