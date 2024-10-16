package kgal.chromosome.base

import kgal.PopulationFactory
import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [ByteArray] of something
 *
 * Use [bytes] function to easily create [ChromosomeByteArray]
 */
public data class ChromosomeByteArray<F : Comparable<F>>(
    override var value: ByteArray,
    override var fitness: F? = null,
) : Chromosome<ByteArray, F> {
    override fun compareTo(other: Chromosome<ByteArray, F>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeByteArray<*>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun clone(): Chromosome<ByteArray, F> = copy(value = value.copyOf())
}

/**
 * Creates [PopulationFactory] for [ChromosomeByteArray] with [size].
 */
public fun <F : Comparable<F>> bytes(size: Int): PopulationFactory<ByteArray, F> =
    { bytes(size) }

/**
 * Create [ChromosomeByteArray] instance.
 * @param size gene count
 */
public fun <F : Comparable<F>> Random.bytes(size: Int): ChromosomeByteArray<F> =
    ChromosomeByteArray(value = nextBytes(size))
