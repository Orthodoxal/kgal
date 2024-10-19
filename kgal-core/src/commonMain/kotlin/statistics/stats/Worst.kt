package kgal.statistics.stats

import kgal.*
import kgal.chromosome.Chromosome
import kgal.statistics.note.Statistic


private const val NAME = "WORST"
private const val NAME_FITNESS = "WORST FITNESS"

/**
 * The worst [Chromosome] in [Population] by fitness
 */
public inline val <V, F> GA<V, F>.worst: Chromosome<V, F>? get() = population.worst

/**
 * The worst fitness of [Chromosome] in [Population]
 */
public inline val <V, F> GA<V, F>.worstFitness: F? get() = population.worst?.fitness

/**
 * The worst [Chromosome] in [Population] by fitness
 */
public inline val <V, F> Lifecycle<V, F>.worst: Chromosome<V, F>?
    get() = if (statisticsConfig.guaranteedSorted) population[size] else population.worst

/**
 * The worst fitness of [Chromosome] in [Population]
 */
public inline val <V, F> Lifecycle<V, F>.worstFitness: F?
    get() = if (statisticsConfig.guaranteedSorted) population[size].fitness else population.worst?.fitness

/**
 * Creates [Statistic] for worst [Chromosome] in [Population] by fitness
 */
public fun <V, F> Lifecycle<V, F>.worst(): Statistic<Chromosome<V, F>?> = Statistic(NAME, worst)

/**
 * Creates [Statistic] for worst fitness of [Chromosome] in [Population]
 */
public fun <F> Lifecycle<*, F>.worstFitness(): Statistic<F?> = Statistic(NAME_FITNESS, worstFitness)
