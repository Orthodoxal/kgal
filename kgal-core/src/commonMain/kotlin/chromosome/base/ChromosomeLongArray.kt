package kgal.chromosome.base

import kgal.PopulationFactory
import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [LongArray] of something
 *
 * Use [longs] function to easily create [ChromosomeLongArray]
 */
public data class ChromosomeLongArray<F : Comparable<F>>(
    override var value: LongArray,
    override var fitness: F? = null,
) : Chromosome<LongArray, F> {
    override fun compareTo(other: Chromosome<LongArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeLongArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<LongArray, F> = copy(value = value.copyOf())
}

/**
 * Creates [PopulationFactory] for [ChromosomeLongArray] with [size].
 * @param from lower bound for the generator (inclusive)
 * @param until upper limit for generator (exclusive)
 */
public fun <F : Comparable<F>> longs(
    size: Int,
    from: Long? = null,
    until: Long? = null,
): PopulationFactory<LongArray, F> =
    { longs(size, from, until) }

/**
 * Create [ChromosomeLongArray] instance.
 * @param size gene count
 * @param from lower bound for the generator (inclusive)
 * @param until upper limit for generator (exclusive)
 */
public fun <F : Comparable<F>> Random.longs(
    size: Int,
    from: Long? = null,
    until: Long? = null,
): ChromosomeLongArray<F> = ChromosomeLongArray(
    value = when {
        from != null && until != null -> LongArray(size) { nextLong(from, until) }
        from != null -> LongArray(size) { nextLong(from, Long.MAX_VALUE) }
        until != null -> LongArray(size) { nextLong(until) }
        else -> LongArray(size) { nextLong() }
    },
)
