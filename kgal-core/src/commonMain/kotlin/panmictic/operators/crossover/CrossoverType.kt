package kgal.panmictic.operators.crossover

import kgal.chromosome.Chromosome
import kgal.cloneOf
import kgal.lastIndex
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
import kgal.panmictic.operators.crossover.CrossoverType.Iterative
import kgal.panmictic.operators.crossover.CrossoverType.Randomly
import kgal.processor.process
import kgal.size
import kgal.utils.randomByChance
import kotlin.random.Random

/**
 * [CrossoverType] describes the strategy of the crossing stage in [PanmicticGA]
 * @see Iterative
 * @see Randomly
 */
public sealed interface CrossoverType {

    /**
     * Iterative crossing:
     * - all chromosomes in pairs in the population have the opportunity to crossing
     * (except the central individual in case of odd population size and depending on the probability of crossing)
     * - parental chromosomes give rise to child chromosomes without cloning (except elite individuals that not change) -
     * crossing occurs as a result of direct modification of the parents' genes
     * - more productive than [Randomly] but less efficient
     *
     * See [iterativeCrossover] for a detailed understanding of how it works
     */
    public data object Iterative : CrossoverType

    /**
     * Randomly crossing:
     * Creates an intermediate copy of the population, performs n crosses in a cycle, where n = (size - elitism) / 2,
     * chromosomes are randomly selected from the previous population, before crossing, the selected individuals are cloned,
     * the new generation fills the intermediate population,
     * after which the intermediate population replaces the previous one.
     * - selection of chromosomes for crossing is random
     * - less productive than the [Iterative]
     * - more efficient (since there is no influence from the order in the population)
     *
     * See [randomlyCrossover] for a detailed understanding of how it works
     */
    public data object Randomly : CrossoverType
}

/**
 * Executes [CrossoverType.Iterative] crossing.
 *
 * Example:
 * ```
 * val elitism = 2
 *
 * Population before: 0, 1, 2, 3, 4, 5, 6
 * Crossover pairs: 0 to 6, 1 to 5, 2 to 4
 * Population after: 0, 1, [2-4], 3, [4-2], [5-1], [6-0]
 * ```
 * @param chance chance of crossover between a pair of chromosomes
 * @param parallelismLimit limit of parallel workers
 * @param crossover specific crossing action (How chromosomes will be crossed)
 * @see [CrossoverType.Iterative]
 */
public suspend inline fun <V, F> PanmicticLifecycle<V, F>.iterativeCrossover(
    chance: Double,
    parallelismLimit: Int,
    crossinline crossover: suspend (chromosome1: Chromosome<V, F>, chromosome2: Chromosome<V, F>, random: Random) -> Unit,
) {
    process(
        parallelismLimit = parallelismLimit,
        startIteration = 0,
        endIteration = size / 2,
        action = { index, random ->
            randomByChance(chance, random) {
                val parent1 = if (index < elitism) population.cloneOf(index) else population[index]
                val parent2 = population[population.lastIndex - index]
                if (parent1 != parent2) crossover(parent1, parent2, random)
            }
        }
    )
}

/**
 * Executes [CrossoverType.Randomly] crossing.
 *
 * Example:
 * ```
 * val elitism = 2
 *
 * Population before: 0, 1, 2, 3, 4, 5, 6
 *
 * Iteration 1 (cross indices = 2, 3):
 *     firstParentIndex = 1
 *     secondParentIndex = 4
 *     Result: [1-4], [4-1]
 *
 * Iteration 2 (cross indices = 4, 5):
 *     firstParentIndex = 1
 *     secondParentIndex = 2
 *     Result: [1-2], [2-1]
 *
 * Population after: 0, 1, [1-4], [4-1], [1-2], [2-1], 6
 * ```
 * @param chance chance of crossover between a pair of chromosomes
 * @param parallelismLimit limit of parallel workers
 * @param crossover specific crossing action (How chromosomes will be crossed)
 * @see [CrossoverType.Randomly]
 */
public suspend inline fun <V, F> PanmicticLifecycle<V, F>.randomlyCrossover(
    chance: Double,
    parallelismLimit: Int,
    crossinline crossover: suspend (chromosome1: Chromosome<V, F>, chromosome2: Chromosome<V, F>, random: Random) -> Unit,
) {
    val tempPopulation = population.copyOf()
    process(
        parallelismLimit = parallelismLimit,
        startIteration = elitism + 1,
        endIteration = size,
        step = 2,
        action = { index, random ->
            randomByChance(chance, random) {
                var index1 = random.nextInt(0, population.lastIndex - 1)
                val index2 = random.nextInt(0, population.lastIndex)
                if (index1 == index2) index1++

                if (population[index1] != population[index2]) {
                    val child1 = population.cloneOf(index1)
                    val child2 = population.cloneOf(index2)
                    crossover(child1, child2, random)
                    tempPopulation[index - 1] = child1
                    tempPopulation[index] = child2
                }
            }
        },
    )
    population.set(tempPopulation)
}
