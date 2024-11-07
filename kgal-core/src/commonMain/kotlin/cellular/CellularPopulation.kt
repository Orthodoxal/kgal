package kgal.cellular

import kgal.AbstractArrayPopulation
import kgal.Population
import kgal.Population.Companion.DEFAULT_POPULATION_NAME
import kgal.PopulationFactory
import kgal.chromosome.Chromosome
import kgal.utils.toroidalShapeIndicesFilter

/**
 * [CellularPopulation] - specific [Population] of [CellularGA] based on [Array] of [Chromosome].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * This population supports:
 * - elitism, see [CellularConfig.elitism]
 * - n-dimensional space for population, see [Dimens]
 *
 * Structure of population with [CellularPopulation.dimens] = `Dimens.rectangle(length = 4, width = 3)`
 * ([CellularPopulation.size] = 4 * 3 = 12):
 *
 * ```
 *     0    1    2    3
 * 0  [0]  [1]  [2]  [3]
 * 1  [4]  [5]  [6]  [7]
 * 2  [8]  [9]  [10] [11]
 * ```
 * `NOTE` Kgal use toroidal coordinates for neighborhoods, for example: the cell with index 3 and VonNeumann(radius = 1) neighborhood
 * the neighbors are the cells with indices: `2, 11, 0, 7`
 *
 * Creates with [population] functions.
 * @see Population
 * @see CellularNeighborhood
 * @see toroidalShapeIndicesFilter
 */
public interface CellularPopulation<V, F> : Population<V, F> {

    /**
     * Dimens for cellular population. Define space and its boundaries for the population and, accordingly, population size.
     * This space determines the position of the chromosomes relative to each other.
     * @see Dimens
     */
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

/**
 * Creates [CellularPopulation].
 * @param dimens Dimens for cellular population. Define space and its boundaries for the population and, accordingly, population size.
 * This space determines the position of the chromosomes relative to each other.
 * @param name name of population, default value = [DEFAULT_POPULATION_NAME], used to identify different populations
 * @param factory [PopulationFactory] for this population. Creates new [Chromosome].
 */
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

/**
 * Creates [CellularPopulation].
 *
 * `NOTE` `population.size` must be equal to `dimens.size`
 * @param dimens Dimens for cellular population. Define space and its boundaries for the population and, accordingly, population size.
 * This space determines the position of the chromosomes relative to each other.
 * @param population initial or existing population.
 * @param name name of population, default value = [DEFAULT_POPULATION_NAME], used to identify different populations
 * @param factory [PopulationFactory] for this population. Creates new [Chromosome].
 */
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

/**
 * Base realization of [CellularPopulation].
 */
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
