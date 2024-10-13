package kgal.chromosome.base

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [DoubleArray] of something
 *
 * Use [doubles] function to easily create [ChromosomeDoubleArray]
 */
public data class ChromosomeDoubleArray<F : Comparable<F>>(
    override var value: DoubleArray,
    override var fitness: F? = null,
) : Chromosome<DoubleArray, F> {
    override fun compareTo(other: Chromosome<DoubleArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeDoubleArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<DoubleArray, F> = copy(value = value.copyOf())
}

/**
 * Create [ChromosomeDoubleArray] instance
 * @param size gene count
 * @param from lower bound for the generator (inclusive)
 * @param until upper limit for generator (exclusive)
 */
public fun <F : Comparable<F>> Random.doubles(
    size: Int,
    from: Double? = null,
    until: Double? = null,
): ChromosomeDoubleArray<F> = ChromosomeDoubleArray(
    value = when {
        from != null && until != null -> DoubleArray(size) { nextDouble(from, until) }
        from != null -> DoubleArray(size) { nextDouble(from, Double.MAX_VALUE) }
        until != null -> DoubleArray(size) { nextDouble(until) }
        else -> DoubleArray(size) { nextDouble() }
    },
)
