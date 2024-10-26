package kgal

import kgal.chromosome.Chromosome

/**
 * Abstract array based [Population].
 * Contains basic implementations of [Population] methods.
 */
public abstract class AbstractArrayPopulation<V, F> : Population<V, F> {

    /**
     * Source population as array of chromosomes.
     */
    protected lateinit var population: Array<Chromosome<V, F>>

    override val initialized: Boolean
        get() = this::population.isInitialized

    override fun get(index: Int): Chromosome<V, F> =
        population[index]

    override fun set(index: Int, chromosome: Chromosome<V, F>): Unit =
        population.set(index, chromosome)

    override fun copyOf(): Array<Chromosome<V, F>> =
        population.copyOf()

    override fun iterator(): Iterator<Chromosome<V, F>> =
        population.iterator()
}
