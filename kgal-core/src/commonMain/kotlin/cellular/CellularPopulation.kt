package kgal.cellular

import kgal.AbstractArrayPopulation
import kgal.Population
import kgal.Population.Companion.DEFAULT_POPULATION_NAME
import kgal.PopulationFactory
import kgal.chromosome.Chromosome

public interface CellularPopulation<V, F> : Population<V, F> {

    public val dimens: Dimens

    /**
     * Get population as an array.
     */
    public fun get(): Array<Chromosome<V, F>>

    /**
     * Set new population.
     */
    public fun set(population: Array<Chromosome<V, F>>)

    /**
     * Override [clone] method to return [CellularPopulation]
     */
    override fun clone(newName: String): CellularPopulation<V, F>
}

public fun <V, F> population(
    dimens: Dimens,
    name: String = DEFAULT_POPULATION_NAME,
    factory: PopulationFactory<V, F>,
): CellularPopulation<V, F> =
    CellularPopulationInstance(
        name = name,
        dimens = dimens,
        factory = factory,
    )

public fun <V, F> population(
    dimens: Dimens,
    population: Array<Chromosome<V, F>>,
    name: String = DEFAULT_POPULATION_NAME,
    factory: PopulationFactory<V, F>,
): CellularPopulation<V, F> =
    CellularPopulationInstance(
        name = name,
        dimens = dimens,
        factory = factory,
        population = population,
    )

internal class CellularPopulationInstance<V, F>(
    override val name: String,
    override val dimens: Dimens,
    override var factory: PopulationFactory<V, F>,
    population: Array<Chromosome<V, F>>? = null,
) : CellularPopulation<V, F>, AbstractArrayPopulation<V, F>() {

    override val size: Int = dimens.size

    init {
        require(size > 0) { "Size must be positive" }

        if (population != null) {
            require(population.size == size) { "Population size must be equal to dimens.size" }
            this.population = population
        }
    }

    override fun get(): Array<Chromosome<V, F>> = population

    override fun set(population: Array<Chromosome<V, F>>) {
        require(population.size == size) { "Population size must be equal to previous population size" }
        this.population = population
    }

    override fun clone(newName: String): CellularPopulation<V, F> =
        CellularPopulationInstance(
            name = name,
            dimens = dimens,
            factory = factory,
            population = Array(size) { index -> population[index].clone() },
        )
}
