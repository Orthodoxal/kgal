package api.population

import api.customization.chromosome.CustomBooleansChromosome
import kgal.PopulationFactory
import kgal.distributed.DistributedPopulation
import kgal.distributed.population

// Constants (can be changed)
private const val CHROMOSOME_SIZE = 100

/**
 * Creating [DistributedPopulation] by custom factory and subpopulations.
 */
internal fun distributedPopulation(): DistributedPopulation<BooleanArray, Int> {
    // STEP 1: create factory function for chromosome
    val factory: PopulationFactory<BooleanArray, Int> = {
        CustomBooleansChromosome(value = BooleanArray(CHROMOSOME_SIZE) { nextBoolean() })
    }
    // STEP 2: create subpopulations mutable list, for example
    val subpopulations = mutableListOf(
        panmicticPopulation(),
        cellularPopulation(),
    )

    // Step 3: create DistributedPopulation
    return population(
        name = "MY_CUSTOM_DISTRIBUTED_POPULATION",
        factory = factory,
        subpopulations = subpopulations,
    )
}
