package kgal.chromosome.base

import kgal.chromosome.Chromosome

/**
 * Base instance for [Chromosome] with [value] as [ShortArray] of something
 */
public data class ChromosomeShortArray<F : Comparable<F>>(
    override var value: ShortArray,
    override var fitness: F? = null,
    private val clone: (ChromosomeShortArray<F>.() -> ChromosomeShortArray<F>)? = null,
) : Chromosome<ShortArray, F> {
    override fun compareTo(other: Chromosome<ShortArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeShortArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<ShortArray, F> = clone?.let { it() } ?: copy(value = value.copyOf())
}
