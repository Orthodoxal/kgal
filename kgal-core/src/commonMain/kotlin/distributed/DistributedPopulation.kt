package kgal.distributed

import kgal.Population
import kgal.PopulationFactory
import kgal.chromosome.Chromosome

/**
 * [DistributedPopulation] - specific [Population] of [Chromosome] for [DistributedGA].
 * Organizes access to [subpopulations] of child GAs as one general population.
 * Tightly tied to the owner's children ([DistributedGA.children]).
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * This population supports:
 * - [subpopulations] - populations, the totality of which determines the [DistributedPopulation], see [subpopulations].
 * A subpopulation of a distributed population can be any population, including another distributed population.
 * - dynamic adding or removing [subpopulations].
 *
 * Creates with [population] functions.
 * @see Population
 */
public interface DistributedPopulation<V, F> : Population<V, F> {

    /**
     * Populations, the totality of which determines the [DistributedPopulation].
     * A subpopulation of a distributed population can be any population, including another distributed population.
     */
    public val subpopulations: List<Population<V, F>>

    /**
     * Adds a [population] to the end of [subpopulations].
     */
    public fun addPopulation(population: Population<V, F>)

    /**
     * Inserts a [population] into the [subpopulations] at the specified [index].
     */
    public fun addPopulation(index: Int, population: Population<V, F>)

    /**
     * Removes a [Population] at the specified [index] from the list.
     * @return the [Population] that has been removed.
     */
    public fun removePopulationAt(index: Int): Population<V, F>

    /**
     * Get population as an array.
     */
    public fun get(): Array<Chromosome<V, F>>

    /**
     * [DistributedPopulation] is considered initialized if there is at least one linked subpopulation
     * and all subpopulations are initialized.
     */
    override val initialized: Boolean

    /**
     * Override [clone] method to return [DistributedPopulation].
     */
    override fun clone(newName: String): DistributedPopulation<V, F>

    public companion object {

        /**
         * Default name for distributed population.
         */
        public const val DEFAULT_DISTRIBUTED_POPULATION_NAME: String = "DISTRIBUTED POPULATION"
    }
}

/**
 * Returns a fullname of [DistributedPopulation] (includes [DistributedPopulation.subpopulations] names).
 */
public val <V, F> DistributedPopulation<V, F>.fullName: String
    get() = "$name for ${subpopulations.joinToString { it.name }}"

/**
 * Creates [DistributedPopulation].
 * @param name name of distributed population, default value = [DistributedPopulation.DEFAULT_DISTRIBUTED_POPULATION_NAME],
 * used to identify different populations
 * @param factory common [PopulationFactory] for [DistributedPopulation.subpopulations]. Creates new [Chromosome].
 * @param subpopulations list of child populations for [DistributedGA]
 */
public fun <V, F> population(
    name: String,
    factory: PopulationFactory<V, F>,
    subpopulations: MutableList<Population<V, F>> = mutableListOf(),
): DistributedPopulation<V, F> =
    DistributedPopulationInstance(
        name = name,
        factory = factory,
        subpopulations = subpopulations,
    )

/**
 * Base realization of [DistributedPopulation].
 */
internal class DistributedPopulationInstance<V, F>(
    override val name: String,
    override var factory: PopulationFactory<V, F>,
    override val subpopulations: MutableList<Population<V, F>>,
) : DistributedPopulation<V, F> {

    override val size: Int
        get() = subpopulations.sumOf { it.size }

    override val initialized: Boolean
        get() = subpopulations.isNotEmpty() && subpopulations.all { it.initialized }

    override fun get(index: Int): Chromosome<V, F> {
        val (actualIndex, child) = findChildByCommonIndex(index)
        return child[actualIndex]
    }

    override fun set(index: Int, chromosome: Chromosome<V, F>) {
        val (actualIndex, child) = findChildByCommonIndex(index)
        child[actualIndex] = chromosome
    }

    override fun addPopulation(population: Population<V, F>) {
        subpopulations.add(population)
    }

    override fun addPopulation(index: Int, population: Population<V, F>) {
        subpopulations.add(index, population)
    }

    override fun removePopulationAt(index: Int): Population<V, F> = subpopulations.removeAt(index)

    override fun get(): Array<Chromosome<V, F>> = copyOf()

    override fun copyOf(): Array<Chromosome<V, F>> {
        val iterator = iterator()
        return Array(size) { iterator.next() }
    }

    override fun iterator(): Iterator<Chromosome<V, F>> = DistributedPopulationIterator()

    /**
     * Special iterator for [DistributedPopulation] based on iterators of [subpopulations].
     */
    private inner class DistributedPopulationIterator : Iterator<Chromosome<V, F>> {
        private var indexChromosome = 0
        private var indexPopulation = 0

        override fun hasNext(): Boolean {
            return when {
                indexPopulation >= subpopulations.size -> false

                indexChromosome >= subpopulations[indexPopulation].size -> {
                    ++indexPopulation
                    indexChromosome = 0
                    hasNext()
                }

                else -> true
            }
        }

        override fun next(): Chromosome<V, F> {
            return try {
                subpopulations[indexPopulation][indexChromosome++]
            } catch (e: IndexOutOfBoundsException) {
                indexChromosome -= 1; throw NoSuchElementException(e.message)
            }
        }
    }

    override fun clone(newName: String): DistributedPopulation<V, F> =
        DistributedPopulationInstance(
            name = newName,
            factory = factory,
            subpopulations = MutableList(subpopulations.size) { index ->
                val child = subpopulations[index]
                child.clone(child.name)
            },
        )
}

/**
 * Find child [Population] in [DistributedPopulation] by common [index].
 * @return [Pair] index in child population and child population
 * @throws NoSuchElementException if there are no [DistributedPopulation.subpopulations]
 * @throws IndexOutOfBoundsException if [index] more than [DistributedPopulation.size]
 */
internal fun <V, F> DistributedPopulationInstance<V, F>.findChildByCommonIndex(
    index: Int
): Pair<Int, Population<V, F>> {
    require(index >= 0) { "Index must be non-negative but was $index" }
    require(index < size) { "Index must be less than distributed population $name size but was $index" }

    var tempIndex = index
    for (child in subpopulations) {
        if (tempIndex < child.size) {
            return tempIndex to child
        }
        tempIndex -= child.size
    }

    if (subpopulations.isEmpty()) {
        throw NoSuchElementException("There is no children populations in distributed population $name")
    } else {
        throw IndexOutOfBoundsException("Index $index out of bounds for distributed population $name")
    }
}
