package kgal.chromosome.base

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [FloatArray] of something
 *
 * Use [floats] function to easily create [ChromosomeFloatArray]
 */
public data class ChromosomeFloatArray<F : Comparable<F>>(
    override var value: FloatArray,
    override var fitness: F? = null,
) : Chromosome<FloatArray, F> {
    override fun compareTo(other: Chromosome<FloatArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeFloatArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<FloatArray, F> = copy(value = value.copyOf())
}

/**
 * Create [ChromosomeFloatArray] instance
 * @param size gene count
 */
public fun <F : Comparable<F>> Random.floats(size: Int): ChromosomeFloatArray<F> =
    ChromosomeFloatArray(value = FloatArray(size) { nextFloat() })
