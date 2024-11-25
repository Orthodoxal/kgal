package api.population

import api.customization.chromosome.CustomBooleansChromosome
import kgal.PopulationFactory
import kgal.panmictic.PanmicticPopulation
import kgal.panmictic.population
import kotlin.random.Random

// Constants (can be changed)
private const val POPULATION_SIZE_INIT = 200
private const val POPULATION_BUFFER = 50
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42

/**
 * Creating [PanmicticPopulation] by custom factory and generated source (populationGen).
 */
internal fun panmicticPopulation(): PanmicticPopulation<BooleanArray, Int> {
    // STEP 1: generate custom population (Or get it from any source)
    val randomGen = Random(seed = RANDOM_SEED)
    // STEP 2: create factory function for chromosome
    val factory: PopulationFactory<BooleanArray, Int> = {
        CustomBooleansChromosome(value = BooleanArray(CHROMOSOME_SIZE) { nextBoolean() })
    }
    // STEP 3: generate initial population and prepare panmictic population source (array) with maximum size: (size + buffer)
    val populationGen = Array(size = POPULATION_SIZE_INIT + POPULATION_BUFFER) { randomGen.factory() }

    // Step 4: create PanmicticPopulation
    return population(
        population = populationGen,
        buffer = POPULATION_BUFFER,
        name = "MY_CUSTOM_POPULATION",
        factory = factory,
    )
}
