package kgal.statistics.stats

import kgal.*
import kgal.chromosome.Chromosome
import kgal.distributed.DistributedEvolveScope
import kgal.statistics.StatisticsConfig
import kgal.statistics.note.Statistic


private const val NAME = "BEST"
private const val NAME_FITNESS = "BEST FITNESS"

/**
 * The best [Chromosome] in [Population] by fitness.
 */
public inline val <V, F> GA<V, F>.best: Chromosome<V, F>? get() = population.best

/**
 * The best fitness of [Chromosome] in [Population].
 */
public inline val <V, F> GA<V, F>.bestFitness: F? get() = population.best?.fitness

/**
 * The best [Chromosome] in [Population] by fitness.
 * Uses a [StatisticsConfig.guaranteedSorted] for optimization,
 * except in the case of a call from [DistributedEvolveScope].
 */
public inline val <V, F> EvolveScope<V, F>.best: Chromosome<V, F>?
    get() {
        return when {
            this is DistributedEvolveScope<V, F> -> population.best
            statisticsConfig.guaranteedSorted -> population.takeIf { !it.isEmpty() }?.get(0)
            else -> population.best
        }
    }

/**
 * The best fitness of [Chromosome] in [Population].
 * Uses a [StatisticsConfig.guaranteedSorted] for optimization,
 * except in the case of a call from [DistributedEvolveScope].
 */
public inline val <V, F> EvolveScope<V, F>.bestFitness: F?
    get() = best?.fitness

/**
 * Creates [Statistic] for best [Chromosome] in [Population] by fitness.
 */
public fun <V, F> EvolveScope<V, F>.best(): Statistic<Chromosome<V, F>?> = Statistic(NAME, best)

/**
 * Creates [Statistic] for best fitness of [Chromosome] in [Population].
 */
public fun <F> EvolveScope<*, F>.bestFitness(): Statistic<F?> = Statistic(NAME_FITNESS, bestFitness)
