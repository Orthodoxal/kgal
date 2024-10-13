package kgal.statistics.stats

import kgal.Lifecycle
import kgal.Population
import kgal.size
import kgal.statistics.note.Statistic


private const val NAME = "SIZE"

/**
 * Creates [Statistic] for size of [Population]
 */
public fun Lifecycle<*, *>.size(): Statistic<Int> = Statistic(NAME, size)
