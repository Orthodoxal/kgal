package kgal.statistics.stats

import kgal.EvolveScope
import kgal.GA
import kgal.Population
import kgal.copyOfRange
import kgal.statistics.note.Statistic
import kotlin.jvm.JvmName


private const val NAME = "MEDIAN"

/**
 * Median fitness value of chromosomes in [Population]
 */
@get:JvmName("getMedianInt")
public val GA<*, Int>.median: Double
    get() = population.getMedian { toDouble() }

/**
 * Median fitness value of chromosomes in [Population]
 */
@get:JvmName("getMedianDouble")
public val GA<*, Double>.median: Double
    get() = population.getMedian { this }

/**
 * Median fitness value of chromosomes in [Population]
 */
@get:JvmName("getMedianLong")
public val GA<*, Long>.median: Double
    get() = population.getMedian { toDouble() }

/**
 * Median fitness value of chromosomes in [Population]
 */
@get:JvmName("getMedianInt")
public val EvolveScope<*, Int>.median: Double
    get() = population.getMedian(guaranteedSorted = statisticsConfig.guaranteedSorted) { toDouble() }

/**
 * Median fitness value of chromosomes in [Population]
 */
@get:JvmName("getMedianDouble")
public val EvolveScope<*, Double>.median: Double
    get() = population.getMedian(guaranteedSorted = statisticsConfig.guaranteedSorted) { this }

/**
 * Median fitness value of chromosomes in [Population]
 */
@get:JvmName("getMedianLong")
public val EvolveScope<*, Long>.median: Double
    get() = population.getMedian(guaranteedSorted = statisticsConfig.guaranteedSorted) { toDouble() }

/**
 * Creates [Statistic] for median fitness value of chromosomes in [Population]
 */
@JvmName("medianInt")
public fun EvolveScope<*, Int>.median(): Statistic<Double> = Statistic(NAME, median)

/**
 * Creates [Statistic] for median fitness value of chromosomes in [Population]
 */
@JvmName("medianDouble")
public fun EvolveScope<*, Double>.median(): Statistic<Double> = Statistic(NAME, median)

/**
 * Creates [Statistic] for median fitness value of chromosomes in [Population]
 */
@JvmName("medianLong")
public fun EvolveScope<*, Long>.median(): Statistic<Double> = Statistic(NAME, median)

/**
 * Get median value from population
 * @param transformer converter [F] to [Double]
 */
private inline fun <V, F> Population<V, F>.getMedian(
    guaranteedSorted: Boolean = false,
    transformer: F.() -> Double,
): Double {
    return if (guaranteedSorted) {
        val indexMedian = size / 2
        if (size % 2 == 1) {
            get(indexMedian).fitness!!.transformer()
        } else {
            val first = get(indexMedian)
            val second = get(indexMedian - 1)
            (first.fitness!!.transformer() + second.fitness!!.transformer()) / 2
        }
    } else {
        val source = this.copyOfRange(fromIndex = 0, toIndex = size).apply { sort() }
        val indexMedian = source.size / 2
        return if (source.size % 2 == 1) {
            source[indexMedian].fitness!!.transformer()
        } else {
            val first = source[indexMedian]
            val second = source[indexMedian - 1]
            (first.fitness!!.transformer() + second.fitness!!.transformer()) / 2
        }
    }
}

