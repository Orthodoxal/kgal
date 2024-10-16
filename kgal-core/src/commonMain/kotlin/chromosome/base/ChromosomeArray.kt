package kgal.chromosome.base

import kgal.PopulationFactory
import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [Array] of something
 *
 * Use [array] function to easily create [ChromosomeArray]
 */
public data class ChromosomeArray<T, F : Comparable<F>>(
    override var value: Array<T>,
    override var fitness: F? = null,
    private val clone: (ChromosomeArray<T, F>.() -> ChromosomeArray<T, F>)? = null,
) : Chromosome<Array<T>, F> {
    override fun compareTo(other: Chromosome<Array<T>, F>): Int = compareValues(fitness, other.fitness)

    override fun clone(): Chromosome<Array<T>, F> = clone?.let { it() } ?: copy(value = value.copyOf())

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeArray<*, *>

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }
}

/**
 * Creates [PopulationFactory] for [ChromosomeArray] with [size].
 * @param factory factory for creating [T] by index
 * @param clone function to correct cloning [ChromosomeArray]
 */
public inline fun <reified T, F : Comparable<F>> booleans(
    size: Int,
    crossinline factory: (index: Int, random: Random) -> T,
    noinline clone: (ChromosomeArray<T, F>.() -> ChromosomeArray<T, F>)?,
): PopulationFactory<Array<T>, F> =
    { array(size, factory, clone) }

/**
 * Create [ChromosomeArray] instance.
 * @param size gene count
 * @param factory factory for randomly generate array of [T]
 */
public inline fun <reified T, F : Comparable<F>> Random.array(
    size: Int,
    factory: (index: Int, random: Random) -> T,
    noinline clone: (ChromosomeArray<T, F>.() -> ChromosomeArray<T, F>)?,
): ChromosomeArray<T, F> = ChromosomeArray(Array(size) { factory(it, this) }, clone = clone)
