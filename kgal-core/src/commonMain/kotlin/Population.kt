package kgal

import kgal.chromosome.Chromosome
import kgal.utils.loop
import kotlin.random.Random

/**
 * Typealias for population factory function
 */
public typealias PopulationFactory<V, F> = Random.() -> Chromosome<V, F>

/**
 * [Population] - base interface for population in GA. Population must be [Iterable].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 * @see AbstractArrayPopulation
 */
public interface Population<V, F> : Iterable<Chromosome<V, F>> {

    /**
     * Name of population, default value = [DEFAULT_POPULATION_NAME]
     */
    public val name: String

    /**
     * [PopulationFactory] for this population. Creates new [Chromosome].
     */
    public var factory: PopulationFactory<V, F>

    /**
     * Current size of population.
     */
    public val size: Int

    /**
     * True if population is initialized and ready to evolve.
     */
    public val initialized: Boolean

    /**
     * Get [Chromosome] by [index]
     */
    public operator fun get(index: Int): Chromosome<V, F>

    /**
     * Set [Chromosome] with [index]
     */
    public operator fun set(index: Int, chromosome: Chromosome<V, F>)

    /**
     * Creates array with chromosomes copied from [Population].
     *
     * Does not create new chromosomes! (Array contains links to original ones).
     * See [clone] to create deep copy.
     */
    public fun copyOf(): Array<Chromosome<V, F>>

    /**
     * Creates deep copy for [Population].
     */
    public fun clone(newName: String = this.name): Population<V, F>

    public companion object {

        /**
         * Default name for population.
         */
        public const val DEFAULT_POPULATION_NAME: String = "POPULATION"
    }
}

/**
 * The best [Chromosome] in [Population] by fitness.
 */
public inline val <V, F> Population<V, F>.best: Chromosome<V, F>? get() = maxOrNull()

/**
 * The worst [Chromosome] in [Population] by fitness.
 */
public inline val <V, F> Population<V, F>.worst: Chromosome<V, F>? get() = minOrNull()

/**
 * Return indices range for [Population]
 */
public inline val Population<*, *>.indices: IntRange get() = 0..<size

public inline val Population<*, *>.lastIndex: Int get() = size - 1

/**
 * Return clone of [Chromosome] with [index] in [Population]
 */
public inline fun <V, F> Population<V, F>.cloneOf(index: Int): Chromosome<V, F> =
    this[index].clone()

/**
 * Returns `true` if the population is empty (contains no chromosomes), `false` otherwise.
 */
public inline fun Population<*, *>.isEmpty(): Boolean =
    !initialized || size <= 0

/**
 * Creates copy of [Population] as an array by range
 * @param fromIndex index chromosome from (inclusive)
 * @param toIndex index chromosome to (exclusive)
 */
public inline fun <V, F> Population<V, F>.copyOfRange(
    fromIndex: Int,
    toIndex: Int,
): Array<Chromosome<V, F>> =
    Array(size = toIndex - fromIndex) { index -> get(fromIndex + index) }

/**
 * Reset population with [factory].
 * @param start index from (inclusive)
 * @param end index to (exclusive)
 */
public inline fun <V, F> Population<V, F>.reset(random: Random, start: Int = 0, end: Int = size) {
    loop(start, end) { index ->
        set(index, random.factory())
    }
}
