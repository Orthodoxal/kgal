package kgal.panmictic

import kgal.Population
import kgal.Population.Companion.DEFAULT_POPULATION_NAME
import kgal.PopulationFactory
import kgal.chromosome.Chromosome
import kgal.panmictic.operators.resize
import kotlin.random.Random

/**
 * [PanmicticPopulation] - specific [Population] of [PanmicticGA] based on [Array] of [Chromosome].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * This population supports:
 * - elitism, see [PanmicticConfig.elitism]
 * - dynamic change [size], see [PanmicticLifecycle.resize].
 * Changing size usually used if the evolutionary strategy involves increasing the population size, see [buffer].
 *
 * Structure of population with [PanmicticGA.elitism] = 2, [size] = 9 and [buffer] = 7:
 *
 * | EC | EC | C | C | C | C | C | C | C | [size] ? | ? | ? | ? | ? | ? | ? | [maxSize]
 *
 * EC - elite active chromosome
 *
 * C - active chromosome
 *
 * ? - free space with generated not active chromosome
 * (Be accurate these chromosome can be not evaluated, that's why they '?').
 *
 * Creates with [population] functions.
 * @see Population
 */
public interface PanmicticPopulation<V, F> : Population<V, F> {

    /**
     * The amount of reserved space in a population.
     * Can be immutable for [PanmicticPopulation] see [PanmicticLifecycle.resize].
     * Usually used if the evolutionary strategy involves increasing the population size.
     *
     * Set [buffer] at the population initialization stage via the corresponding parameter.
     *
     * NOTE:
     * - Can be automatically changed when the [size] is changed
     * - Always must be positive or zero
     * - Dynamically can be changed with [PanmicticLifecycle.resize]
     *
     * Default value is zero
     */
    public val buffer: Int

    /**
     * Current size of population. Can be immutable for [PanmicticPopulation] see [PanmicticLifecycle.resize].
     *
     * NOTE:
     * - Always must be positive
     * - Dynamically can be changed with [PanmicticLifecycle.resize]
     *
     * @see buffer param
     */
    public override val size: Int

    /**
     * Maximum population size: [size] + [buffer]
     */
    public val maxSize: Int

    /**
     * Get population as an array.
     */
    public fun get(): Array<Chromosome<V, F>>

    /**
     * Set new population.
     *
     * Note:
     * - May cause [size] and [buffer] to change
     */
    public fun set(population: Array<Chromosome<V, F>>)

    /**
     * Set new sizes of population.
     *
     * NOTE!
     * - Do not use it directly cause chromosomes in range: oldSize..<newSize can be not evaluated!
     * Use safely [PanmicticLifecycle.resize] operator.
     * - Changing only [newSize] ([newBuffer] is null) will automatically calculate the value of [buffer]
     *
     * @param newSize Set as new [size] value. If null - no change [size].
     * - Always must be positive
     * - When decreasing the value, the array will not be copied.
     * The freed space will be marked as buffer - the value of [buffer] will be increased by oldSize - newSize
     * - When increasing the value AND new value less or equal to [maxSize] - the array will not be copied.
     * Space will be taken from buffer - the value of [buffer] will be decreased by newSize - oldSize
     * - When increasing the value AND new value more than [maxSize] - the existing population being copied to a new array,
     * which can take a significant amount of time. [buffer] will be set to 0
     * @param newBuffer Set as new [buffer] value. If null - no change [buffer] (But [buffer] can be changed by changing [size], see above).
     * - Always must be positive or zero
     * - Manual changing [buffer] will copy the existing population to a new array,
     * which can take a significant amount of time
     */
    public fun resize(
        newSize: Int? = null,
        newBuffer: Int? = null,
    )

    /**
     * Sort population ([Chromosome] is [Comparable]) in descending order by default.
     */
    public fun sort(ascending: Boolean = false): Array<Chromosome<V, F>>

    /**
     * Override [clone] method to return [PanmicticPopulation]
     */
    override fun clone(newName: String): PanmicticPopulation<V, F>
}

/**
 * Creates [PanmicticPopulation].
 * @param size the current size of population
 * @param buffer the amount of reserved space in a population.
 * If the expected maximum value of the population size (MAX - to which growth is possible) is known, improve performance by:
 *
 * [buffer] = MAX - [size]
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
    size: Int,
    buffer: Int,
    override var factory: PopulationFactory<V, F>,
    population: Array<Chromosome<V, F>>? = null,
) : PanmicticPopulation<V, F> {

    private lateinit var population: Array<Chromosome<V, F>>

    init {
        require(size > 0) { "Size must be positive" }
        require(buffer >= 0) { "Buffer must be positive or zero" }

        if (population != null) {
            require(population.size == size + buffer) {
                "Size of initialized population must be equal to size + buffer"
            }
            this.population = population
        }
    }

    override val initialized: Boolean
        get() = this::population.isInitialized

    override var size: Int = size
        private set

    override var buffer: Int = buffer
        private set

    override val maxSize: Int get() = size + buffer

    override fun get(): Array<Chromosome<V, F>> = population

    override fun set(population: Array<Chromosome<V, F>>) {
        this.population = population
        when {
            population.size == maxSize -> Unit

            population.size < maxSize -> {
                if (population.size < size) {
                    size = population.size
                    buffer = 0
                } else {
                    buffer = population.size - size
                }
            }

            population.size > maxSize -> {
                buffer = population.size - size
            }
        }
    }

    override fun get(index: Int): Chromosome<V, F> =
        population[index]

    override fun set(index: Int, chromosome: Chromosome<V, F>) =
        population.set(index, chromosome)

    override fun copyOf(): Array<Chromosome<V, F>> =
        population.copyOf()

    override fun resize(newSize: Int?, newBuffer: Int?) {
        if (newSize == null && newBuffer == null) return // no changes

        val updatedSize = newSize ?: size
        val updatedBuffer = newBuffer
        // if updatedSize >= maxSize && newBuffer == null -> buffer overflow: updatedBuffer = 0
            ?: (maxSize - updatedSize).coerceAtLeast(minimumValue = 0)

        // checks for new values
        require(updatedSize > 0) { "Size must be positive" }
        require(updatedBuffer >= 0) { "Buffer must be positive or zero" }

        if (!this::population.isInitialized) {
            // population is not initialized -> set sizes safely and return
            size = updatedSize
            buffer = updatedBuffer
            return
        }

        if (updatedSize <= maxSize) {
            if (newBuffer != null && updatedBuffer != maxSize - updatedSize) {
                // new size is less than previous maxSize, buffer changed manually && change is not predictably ->
                // create new array
                population =
                    if (updatedBuffer < maxSize - updatedSize) {
                        // New population array less than previous (changed by newBuffer)
                        // TODO add warning logger (Changing buffer to less value)
                        Array(updatedSize + updatedBuffer) { index -> population[index] }
                    } else {
                        // New population array more than previous (changed by newBuffer)
                        Array(updatedSize + updatedBuffer) { index ->
                            if (index < maxSize) population[index] else Random.factory()
                        }
                    }
            }
            // new size is less than previous maxSize, buffer not changed manually (is null) or changed predictably ->
            // not need create population cause maxSize not changed
        } else {
            // new size more than previous maxSize -> create new array
            // TODO add warning logger (Changing size value more than maxSize? Possible init population with buffer?)
            population = Array(updatedSize + updatedBuffer) { index ->
                if (index < maxSize) population[index] else Random.factory()
            }
        }

        size = updatedSize
        buffer = updatedBuffer
    }

    override fun sort(ascending: Boolean): Array<Chromosome<V, F>> {
        if (ascending) {
            population.sort(fromIndex = 0, toIndex = size)
        } else {
            population.sortDescending(fromIndex = 0, toIndex = size)
        }
        return population
    }

    override fun clone(newName: String): PanmicticPopulation<V, F> =
        PanmicticPopulationInstance(
            name = newName,
            size = size,
            buffer = buffer,
            factory = factory,
            population = Array(maxSize) { index -> population[index].clone() },
        )

    override fun iterator(): Iterator<Chromosome<V, F>> =
        PanmicticPopulationIterator(population, size)

    private class PanmicticPopulationIterator<V, F>(
        private val population: Array<Chromosome<V, F>>,
        private val currentSize: Int,
    ) : Iterator<Chromosome<V, F>> {
        private var index = 0
        override fun hasNext(): Boolean = index < currentSize
        override fun next(): Chromosome<V, F> =
            try {
                population[index++]
            } catch (e: NoSuchElementException) {
                index -= 1; throw NoSuchElementException(e.message)
            }
    }
}
