package kgal.chromosome.base

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [BooleanArray] of something
 *
 * Use [booleans] function to easily create [ChromosomeBooleanArray]
 */
public data class ChromosomeBooleanArray<F : Comparable<F>>(
    override var value: BooleanArray,
    override var fitness: F? = null,
) : Chromosome<BooleanArray, F> {
    override fun compareTo(other: Chromosome<BooleanArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeBooleanArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<BooleanArray, F> = copy(value = value.copyOf())
}

/**
 * Create [ChromosomeBooleanArray] instance
 * @param size gene count
 */
public fun <F : Comparable<F>> Random.booleans(size: Int): ChromosomeBooleanArray<F> =
    ChromosomeBooleanArray(value = BooleanArray(size) { nextBoolean() })
