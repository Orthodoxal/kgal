package kgal.distributed

import kgal.Population

/**
 * Factory for building [DistributedPopulation] by subpopulations with [populationIndex].
 *
 * Creates with DistributedPopulationMultiFactory().
 */
public interface DistributedPopulationMultiFactory {

    /**
     * Special current index for subpopulations used for auto generating population's names.
     * @see generateChildName
     */
    public var populationIndex: Int
}

/**
 * Resets [DistributedPopulationMultiFactory.populationIndex] to 0.
 */
public fun DistributedPopulationMultiFactory.reset() {
    populationIndex = 0
}

/**
 * Produce [Population] with [DistributedPopulationMultiFactory].
 * Expect [DistributedPopulationMultiFactory.populationIndex] is 0.
 * @see reset
 */
public inline fun <V, F, T : Population<V, F>> DistributedPopulationMultiFactory.produce(
    producer: DistributedPopulationMultiFactory.() -> T,
): T = producer(this).also { ++populationIndex }

/**
 * Creates [DistributedPopulationMultiFactory].
 */
public fun DistributedPopulationMultiFactory(): DistributedPopulationMultiFactory =
    DistributedPopulationMultiFactoryInstance(populationIndex = 0)

/**
 * Base instance of [DistributedPopulationMultiFactory].
 */
internal class DistributedPopulationMultiFactoryInstance(
    override var populationIndex: Int
) : DistributedPopulationMultiFactory
