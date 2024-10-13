package kgal.statistics.note

import kotlin.reflect.KProperty

/**
 * Base interface for [Statistic]. Used as field of [StatisticNote].
 *
 * Creates with [Statistic].
 */
public interface Statistic<out V> {

    /**
     * name - includes a name for the statistical data
     * For example: "BEST", "WORST", "AVG"
     */
    public val name: String

    /**
     * value - includes value for the statistical data
     * For example: "25", "12", "15.4"
     */
    public val value: V

    public operator fun getValue(thisRef: Any?, property: KProperty<*>): V = value

    public operator fun component1(): String = name
    public operator fun component2(): V = value
}

/**
 * Creates [Statistic] using [StatisticInstance].
 * @see [Statistic]
 */
public fun <V> Statistic(name: String, value: V): Statistic<V> = StatisticInstance(name, value)

/**
 * Base instance for [Statistic].
 */
internal class StatisticInstance<V>(
    override val name: String,
    override val value: V,
) : Statistic<V>
