package kgal.chromosome.base

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [IntArray] of something
 *
 * Use [ints] function to easily create [ChromosomeIntArray]
 */
public data class ChromosomeIntArray<F : Comparable<F>>(
    override var value: IntArray,
    override var fitness: F? = null,
) : Chromosome<IntArray, F> {
    override fun compareTo(other: Chromosome<IntArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeIntArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<IntArray, F> = copy(value = value.copyOf())
}

/**
 * Create [ChromosomeIntArray] instance
 * @param size gene count
 */
public fun <F : Comparable<F>> Random.ints(
    size: Int,
    from: Int? = null,
    until: Int? = null,
): ChromosomeIntArray<F> = ChromosomeIntArray(
    value = when {
        from != null && until != null -> IntArray(size) { nextInt(from, until) }
        from != null -> IntArray(size) { nextInt(from, Int.MAX_VALUE) }
        until != null -> IntArray(size) { nextInt(until) }
        else -> IntArray(size) { nextInt() }
    },
)
