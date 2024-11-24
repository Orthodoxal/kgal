package api.population

import api.customization.chromosome.CustomBooleansChromosome
import kgal.PopulationFactory
import kgal.cellular.CellularPopulation
import kgal.cellular.Dimens
import kgal.cellular.population
import kotlin.random.Random

// Constants (can be changed)
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42

/**
 * Creating [CellularPopulation] by custom factory and generated source (populationGen).
 */
internal fun cellularPopulation(): CellularPopulation<BooleanArray, Int> {
    // STEP 1: generate custom population (Or get it from any source)
    val randomGen = Random(seed = RANDOM_SEED)
    // STEP 2: create factory function for chromosome
    val factory: PopulationFactory<BooleanArray, Int> = {
        CustomBooleansChromosome(value = BooleanArray(CHROMOSOME_SIZE) { nextBoolean() })
    }
    // STEP 3: create dimens of Cellular Population (dimens define the population space)
    val dimens = Dimens.custom(5, 10, 3, 5)
    // STEP 4: generate initial population and prepare cellular population source (array)
    val populationGen = Array(size = 5 * 10 * 3 * 5) { randomGen.factory() }

    // Step 5: create CellularPopulation
    return population(
        dimens = dimens,
        population = populationGen,
        name = "MY_CUSTOM_POPULATION",
        factory = factory,
    )
}
