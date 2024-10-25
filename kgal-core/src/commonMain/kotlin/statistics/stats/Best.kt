package kgal.statistics.stats

import kgal.GA
import kgal.Lifecycle
import kgal.Population
import kgal.best
import kgal.chromosome.Chromosome
import kgal.statistics.note.Statistic


private const val NAME = "BEST"
private const val NAME_FITNESS = "BEST FITNESS"

/**
 * The best [Chromosome] in [Population] by fitness
 */
public inline val <V, F> GA<V, F>.best: Chromosome<V, F>? get() = population.best

/**
 * The best fitness of [Chromosome] in [Population]
 */
public inline val <V, F> GA<V, F>.bestFitness: F? get() = population.best?.fitness

/**
 * The best [Chromosome] in [Population] by fitness
 */
public inline val <V, F> Lifecycle<V, F>.best: Chromosome<V, F>?
    get() = if (statisticsConfig.guaranteedSorted) population[0] else population.best

/**
 * The best fitness of [Chromosome] in [Population]
 */
public inline val <V, F> Lifecycle<V, F>.bestFitness: F?
    get() = if (statisticsConfig.guaranteedSorted) population[0].fitness else population.best?.fitness

/**
 * Creates [Statistic] for best [Chromosome] in [Population] by fitness
 */
public fun <V, F> Lifecycle<V, F>.best(): Statistic<Chromosome<V, F>?> = Statistic(NAME, best)

/**
 * Creates [Statistic] for best fitness of [Chromosome] in [Population]
 */
public fun <F> Lifecycle<*, F>.bestFitness(): Statistic<F?> = Statistic(NAME_FITNESS, bestFitness)
