package kgal.panmictic

import kgal.Population
import kgal.Population.Companion.DEFAULT_POPULATION_NAME
import kgal.PopulationFactory
import kgal.chromosome.Chromosome

/**
 * [PanmicticPopulation] - specific [Population] of [PanmicticGA].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * Creates with [population] functions.
 * @see Population
 */
public interface PanmicticPopulation<V, F> : Population<V, F> {

    /**
     * The amount of reserved space in a population.
     * Used if the evolutionary strategy involves increasing the population size.
     */
    public var buffer: Int

    /**
     * Maximum population size: [size] + [buffer]
     */
    public val maxSize: Int

    /**
     * Get population as an array
     */
    public fun get(): Array<Chromosome<V, F>>

    /**
     * Override [clone] method to return [PanmicticPopulation]
     */
    override fun clone(newName: String): PanmicticPopulation<V, F>
}

/**
 * Creates [PanmicticPopulation].
 * @param size current size of population
 * @param buffer the amount of reserved space in a population.
 * @param name name of population, default value = [DEFAULT_POPULATION_NAME], used to identify different populations
 * @param factory [PopulationFactory] for this population. Creates new [Chromosome].
 */
public fun <V, F> population(
    size: Int,
    buffer: Int = 0,
    name: String = DEFAULT_POPULATION_NAME,
    factory: PopulationFactory<V, F>,
): PanmicticPopulation<V, F> =
    PanmicticPopulationInstance(
        name = name,
        size = size,
        buffer = buffer,
        factory = factory,
    )

/**
 * Creates [PanmicticPopulation].
 * @param population initial or existing population.
 * Size of population will be equal to [Array.size] - [buffer]. (Default buffer is 0)
 * @param buffer the amount of reserved space in a population.
 * @param name name of population, default value = [DEFAULT_POPULATION_NAME], used to identify different populations
 * @param factory [PopulationFactory] for this population. Creates new [Chromosome].
 */
public fun <V, F> population(
    population: Array<Chromosome<V, F>>,
    buffer: Int = 0,
    name: String = DEFAULT_POPULATION_NAME,
    factory: PopulationFactory<V, F>,
): PanmicticPopulation<V, F> =
    PanmicticPopulationInstance(
        name = name,
        size = population.size - buffer,
        buffer = buffer,
        factory = factory,
        population = population,
    )

/**
 * Base realization of [PanmicticPopulation].
 */
internal class PanmicticPopulationInstance<V, F>(
    override val name: String,
    override var size: Int,
    buffer: Int,
    override var factory: PopulationFactory<V, F>,
    population: Array<Chromosome<V, F>>? = null,
) : PanmicticPopulation<V, F> {

    private lateinit var population: Array<Chromosome<V, F>>

    init {
        if (population != null) {
            this.population = population
        }
    }

    override val initialized: Boolean
        get() = this::population.isInitialized && population.size >= size

    override var buffer: Int = buffer
        set(value) {
            field = value
            population = Array(maxSize) { index ->
                if (index < population.size) population[index] else population.first()
            }
        }

    override val maxSize: Int get() = population.size

    override fun get(): Array<Chromosome<V, F>> = population

    override fun set(population: Array<Chromosome<V, F>>) {
        this.population = population
    }

    override fun get(index: Int): Chromosome<V, F> =
        population[index]

    override fun set(index: Int, chromosome: Chromosome<V, F>) =
        population.set(index, chromosome)

    override fun copyOf(): Array<Chromosome<V, F>> =
        population.copyOf()

    override fun clone(newName: String): PanmicticPopulation<V, F> =
        PanmicticPopulationInstance(
            name = newName,
            size = size,
            buffer = buffer,
            factory = factory,
            population = Array(maxSize) { index -> population[index].clone() },
        )

    override fun iterator(): Iterator<Chromosome<V, F>> =
        population.iterator()
}
