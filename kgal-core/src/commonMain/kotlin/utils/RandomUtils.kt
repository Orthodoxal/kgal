package kgal.utils

import kgal.EvolveScope
import kgal.Population
import kgal.chromosome.Chromosome
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

private const val EQUALS_DELTA = 0.000001

/**
 * Performs comparisons between real numbers using [delta] parameter
 */
public fun Double.equalsDelta(other: Double, delta: Double = EQUALS_DELTA): Boolean =
    abs(this / other - 1) < delta


/**
 * Performs comparisons between real numbers using [equalsDelta]
 */
public infix fun Double.moreOrEquals(other: Double): Boolean =
    this > other || this.equalsDelta(other)

/**
 * Performs comparisons between real numbers using [equalsDelta]
 */
public infix fun Double.lessOrEquals(other: Double): Boolean =
    this < other || this.equalsDelta(other)

/**
 * Returns true with probability [chance] (0.0 < 1.0)
 *
 * chance <= 0.0 -> return false
 *
 * chance >= 1.0 -> return true
 */
public fun randomByChance(chance: Double, random: Random): Boolean =
    chance lessOrEquals 0.0 && chance moreOrEquals 1.0 || chance > random.nextDouble(0.0, 1.0)

/**
 * Execute action with probability [chance] (0.0 < 1.0)
 *
 * chance <= 0.0 -> return false
 *
 * chance >= 1.0 -> return true
 * @see randomByChance
 */
public inline fun randomByChance(chance: Double, random: Random, action: () -> Unit): Unit =
    if (randomByChance(chance, random)) action() else Unit

/**
 * Execute action with probability [chance] (0.0 < 1.0)
 *
 * chance <= 0.0 -> return false
 *
 * chance >= 1.0 -> return true
 * @see randomByChance
 */
public inline fun EvolveScope<*, *>.randomByChance(chance: Double, action: () -> Unit): Unit =
    randomByChance(chance, random, action)

/**
 * Checks if an element is included in an array
 * @param elem element to find in array
 * @param subSize size limit for subarray
 */
public inline fun IntArray.inUntil(elem: Int, subSize: Int): Boolean {
    for (i in 0..<subSize) {
        if (get(i) == elem) return true
    }
    return false
}

/**
 * Creates [IntArray] with random indices
 * @param count number of indices
 */
public fun IntRange.indicesByRandom(count: Int, random: Random): IntArray {
    if (count > last - first) throw IllegalStateException("Count cannot be more than size")
    val randomIndices = IntArray(count)
    repeat(count) { counter ->
        var index = random.nextInt(first, last)
        while (randomIndices.inUntil(index, counter)) {
            index = ++index % last
        }
        randomIndices[counter] = index
    }
    return randomIndices
}

/**
 * Creates [IntArray] with random indices
 * @param count number of indices, `count < size`
 * @param size maximum indices count (exclusive)
 */
public fun indicesByRandom(count: Int, size: Int, random: Random): IntArray {
    if (count > size) throw IllegalStateException("Count cannot be more than size")

    return if (size > 5000 && count > size / 4) {
        val newAr = IntArray(size) { it }
        newAr.shuffle(random)
        newAr.copyOf(count)
    } else {
        val randomIndices = IntArray(count)
        repeat(count) { counter ->
            var index = random.nextInt(0, size)
            while (randomIndices.inUntil(index, counter)) {
                index = ++index % size
            }
            randomIndices[counter] = index
        }
        randomIndices
    }
}

/**
 * Creates [Array] with random elements from source array
 * @param count number of random elements
 */
public inline fun <reified T> Array<out T>.random(count: Int, random: Random): Array<T> {
    val indices = indicesByRandom(count, size, random)
    return Array(count) { get(indices[it]) }
}

/**
 * Creates [Array] with randomly selected chromosomes from source array
 * @param count number of random elements
 * @return [Pair] of [Array] and [IntArray] (indices from source)
 */
public inline fun <V, F> Population<V, F>.randomWithIndices(
    count: Int,
    random: Random
): Pair<Array<Chromosome<V, F>>, IntArray> {
    val indices = indicesByRandom(count, size, random)
    return Array(count) { get(indices[it]) } to indices
}

/**
 * Next Gaussian realization
 * @param mean average.
 * Is the mathematical expectation (mean value) of the normal distribution from which the random value will be returned.
 * It defines the center of the distribution.
 * @param stddev standard deviation.
 * It is a measure of the spread or variability of values around the mean.
 * The larger the value of the standard deviation, the wider the distribution,
 * and therefore the values will be more spread out around the mean.
 */
public fun Random.nextGaussian(mean: Double, stddev: Double): Double {
    var v1: Double
    var v2: Double
    var s: Double
    do {
        v1 = 2 * nextDouble() - 1
        v2 = 2 * nextDouble() - 1
        s = v1 * v1 + v2 * v2
    } while (s >= 1 || s.equalsDelta(0.0))
    val multiplier = sqrt(-2 * ln(s) / s)
    return mean + stddev * (v1 * multiplier)
}
