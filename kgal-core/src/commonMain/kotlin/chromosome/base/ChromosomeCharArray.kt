package kgal.chromosome.base

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [CharArray] of something
 *
 * Use [chars] function to easily create [ChromosomeCharArray]
 */
public data class ChromosomeCharArray<F : Comparable<F>>(
    override var value: CharArray,
    override var fitness: F? = null,
) : Chromosome<CharArray, F> {
    override fun compareTo(other: Chromosome<CharArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeCharArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<CharArray, F> = copy(value = value.copyOf())
}

internal val defaultAllowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
internal fun chars(size: Int, random: Random, allowedChars: List<Char> = defaultAllowedChars) =
    CharArray(size) { allowedChars.random(random) }

/**
 * Create [ChromosomeCharArray] instance
 * @param size gene count
 * @param allowedChars symbols for generation
 * @see defaultAllowedChars
 */
public fun <F : Comparable<F>> Random.chars(
    size: Int,
    allowedChars: List<Char> = defaultAllowedChars,
): ChromosomeCharArray<F> = ChromosomeCharArray(value = chars(size, this, allowedChars))
