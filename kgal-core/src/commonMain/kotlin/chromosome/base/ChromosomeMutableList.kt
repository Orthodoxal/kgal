package kgal.chromosome.base

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Base instance for [Chromosome] with [value] as [MutableList] of something
 *
 * Use [mutableList] function to easily create [ChromosomeMutableList]
 */
public data class ChromosomeMutableList<T, F : Comparable<F>>(
    override var value: MutableList<T>,
    override var fitness: F? = null,
    private val clone: (ChromosomeMutableList<T, F>.() -> ChromosomeMutableList<T, F>)? = null,
) : Chromosome<MutableList<T>, F> {
    override fun compareTo(other: Chromosome<MutableList<T>, F>): Int = compareValues(fitness, other.fitness)

    override fun clone(): Chromosome<MutableList<T>, F> = clone?.let { it() } ?: copy(value = value.toMutableList())
}

/**
 * Create [ChromosomeMutableList] instance
 * @param size gene count
 * @param factory factory for randomly generate array [ChromosomeMutableList.value]
 */
public inline fun <reified T, F : Comparable<F>> Random.mutableList(
    size: Int,
    factory: (index: Int, random: Random) -> T,
    noinline clone: (ChromosomeMutableList<T, F>.() -> ChromosomeMutableList<T, F>)? = null,
): ChromosomeMutableList<T, F> = ChromosomeMutableList(MutableList(size) { factory(it, this) }, clone = clone)
