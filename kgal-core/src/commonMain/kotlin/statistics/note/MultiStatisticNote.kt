package kgal.statistics.note

import kgal.EvolveScope
import kgal.GA
import kgal.name
import kotlin.jvm.JvmName

/**
 * [MultiStatisticNote] - multi record statistics.
 *
 * Unlike [SingleStatisticNote], it collects several statistics and creates one general record.
 * @see StatisticNote
 */
public data class MultiStatisticNote(
    val statistics: List<Statistic<Any?>>,
    override val ownerName: String,
    override val iteration: Int,
) : StatisticNote<Any?> {
    override val statistic: Statistic<Any?>
        get() = Statistic(
            name = statistics.joinToString { it.name },
            value = statistics.map { it.value },
        )

    override fun toString(): String {
        return statistics.joinToString(separator = "") { (name, value) -> "$ownerName\t $iteration\t $name\t $value\n" }
    }
}

/**
 * Creates [MultiStatisticNote] and send it to [GA.statisticsProvider]
 *
 * Example:
 * ```
 * evolution {
 *     stat(
 *         "BEST" to best,
 *         "WORST" to worst,
 *     )
 * }
 *
 * ```
 * @see MultiStatisticNote
 */
@JvmName("statValue")
public suspend fun EvolveScope<*, *>.stat(
    vararg pairs: Pair<String, Any?>,
): Unit = emitStat(
    MultiStatisticNote(
        statistics = pairs.map { Statistic(it.first, it.second) },
        ownerName = this.name,
        iteration = this.iteration,
    )
)

/**
 * Creates [MultiStatisticNote] and send it to [GA.statisticsProvider]
 *
 * Example:
 * ```
 * evolution {
 *     stat(
 *         "CUSTOM_1" to {
 *            val res1 = function1(param1 = best, param2 = worst)
 *            val res2 = function2(param1 = best, param2 = worst)
 *            res1 + res2
 *         },
 *         "CUSTOM_2" to {
 *            val res3 = function3(param1 = best, param2 = worst)
 *            val res4 = function4(param1 = best, param2 = worst)
 *            res3 + res4
 *         },
 *     )
 * }
 *
 * ```
 * @see MultiStatisticNote
 */
@JvmName("statRegister")
public suspend fun EvolveScope<*, *>.stat(
    vararg pairs: Pair<String, () -> Any?>,
): Unit = emitStat(
    MultiStatisticNote(
        statistics = pairs.map { Statistic(it.first, it.second()) },
        ownerName = this.name,
        iteration = this.iteration,
    )
)

/**
 * Creates [MultiStatisticNote] and send it to [GA.statisticsProvider]
 *
 * Example:
 * ```
 * evolution {
 *     stat(median(), mean(), bestFitness(), worstFitness())
 * }
 *
 * ```
 * @see MultiStatisticNote
 */
public suspend fun EvolveScope<*, *>.stat(
    vararg statistics: Statistic<Any?>,
): Unit = emitStat(
    MultiStatisticNote(
        statistics = statistics.toList(),
        ownerName = this.name,
        iteration = this.iteration,
    )
)
