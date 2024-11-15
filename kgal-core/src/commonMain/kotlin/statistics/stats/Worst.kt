package kgal.statistics.stats

import kgal.*
import kgal.chromosome.Chromosome
import kgal.distributed.DistributedLifecycle
import kgal.statistics.StatisticsConfig
import kgal.statistics.note.Statistic


private const val NAME = "WORST"
private const val NAME_FITNESS = "WORST FITNESS"

/**
 * The worst [Chromosome] in [Population] by fitness.
 */
public inline val <V, F> GA<V, F>.worst: Chromosome<V, F>? get() = population.worst

/**
 * The worst fitness of [Chromosome] in [Population].
 */
public inline val <V, F> GA<V, F>.worstFitness: F? get() = population.worst?.fitness

/**
 * The worst [Chromosome] in [Population] by fitness.
 * Uses a [StatisticsConfig.guaranteedSorted] for optimization,
 * except in the case of a call from [DistributedLifecycle].
 */
public inline val <V, F> Lifecycle<V, F>.worst: Chromosome<V, F>?
    get() {
        return when {
            this is DistributedLifecycle<V, F> -> population.worst
            statisticsConfig.guaranteedSorted -> population.takeIf { !it.isEmpty() }?.get(size - 1)
            else -> population.worst
        }
    }

/**
 * The worst fitness of [Chromosome] in [Population].
 * Uses a [StatisticsConfig.guaranteedSorted] for optimization,
 * except in the case of a call from [DistributedLifecycle].
 */
public inline val <V, F> Lifecycle<V, F>.worstFitness: F?
    get() = worst?.fitness

/**
 * Creates [Statistic] for worst [Chromosome] in [Population] by fitness.
 */
public fun <V, F> Lifecycle<V, F>.worst(): Statistic<Chromosome<V, F>?> = Statistic(NAME, worst)

/**
 * Creates [Statistic] for worst fitness of [Chromosome] in [Population].
 */
public fun <F> Lifecycle<*, F>.worstFitness(): Statistic<F?> = Statistic(NAME_FITNESS, worstFitness)
