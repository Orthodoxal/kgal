package kgal.statistics.note

import kgal.GA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

/**
 * [StatisticNote] - general abstraction for a statistical record that includes:
 * - statistical data (calculated statistical information)
 * - owner's name (name of population on which the calculation was performed)
 * - iteration ([GA.iteration] on which the calculation was performed)
 *
 * @see SingleStatisticNote
 * @see MultiStatisticNote
 * @see Statistic
 */
public sealed interface StatisticNote<V> {

    /**
     * Statistical data (calculated statistical information)
     */
    public val statistic: Statistic<V>

    /**
     * Owner's name (name of population on which the calculation was performed)
     */
    public val ownerName: String

    /**
     * Iteration ([GA.iteration] on which the calculation was performed)
     */
    public val iteration: Int
}

@Suppress("UNCHECKED_CAST")
public inline fun <reified V> Flow<StatisticNote<V>>.withName(name: String): Flow<SingleStatisticNote<V>> =
    this.mapNotNull { note ->
        when (note) {
            is SingleStatisticNote<V> -> note.takeIf { it.statistic.name == name }
            is MultiStatisticNote -> {
                val statistic = note.statistics.firstOrNull { it.name == name } as? Statistic<V>
                    ?: return@mapNotNull null
                SingleStatisticNote(statistic, note.ownerName, note.iteration)
            }
        }
    }

public fun Flow<StatisticNote<Any?>>.withNames(vararg names: String): Flow<StatisticNote<Any?>> {
    val setNames = names.toSet()
    val store = mutableListOf<Statistic<Any?>>()
    return this.mapNotNull { note ->
        when (note) {
            is SingleStatisticNote<*> -> note.takeIf { setNames.contains(it.statistic.name) }
            is MultiStatisticNote -> {
                for (stat in note.statistics) {
                    if (setNames.contains(stat.name)) {
                        store.add(stat)
                    }
                }
                return@mapNotNull when (store.size) {
                    0 -> null
                    1 -> SingleStatisticNote(store.first(), note.ownerName, note.iteration)
                    setNames.size -> note
                    else -> MultiStatisticNote(store.toMutableList(), note.ownerName, note.iteration)
                }.also { store.clear() }
            }
        }
    }
}

