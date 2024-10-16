package kgal.chromosome.base

import kgal.PopulationFactory
import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [String] of something
 *
 * Use [string] function to easily create [ChromosomeString]
 */
public data class ChromosomeString<F : Comparable<F>>(
    override var value: String,
    override var fitness: F? = null,
) : Chromosome<String, F> {
    override fun compareTo(other: Chromosome<String, F>): Int = compareValues(fitness, other.fitness)

    override fun clone(): Chromosome<String, F> = copy(value = value)
}

/**
 * Creates [PopulationFactory] for [ChromosomeString] with [size].
 * @param allowedChars symbols for generation
 * @see defaultAllowedChars
 */
public fun <F : Comparable<F>> string(
    size: Int,
    allowedChars: List<Char> = defaultAllowedChars,
): PopulationFactory<String, F> =
    { string(size, allowedChars) }

/**
 * Create [ChromosomeString] instance
 * @param size gene count
 * @param allowedChars symbols for generation
 * @see defaultAllowedChars
 */
public fun <F : Comparable<F>> Random.string(
    size: Int,
    allowedChars: List<Char> = defaultAllowedChars,
): ChromosomeString<F> =
    ChromosomeString(value = chars(size, this, allowedChars).concatToString())
