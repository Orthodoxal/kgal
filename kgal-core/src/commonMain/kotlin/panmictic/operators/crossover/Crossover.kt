package kgal.panmictic.operators.crossover

import kgal.chromosome.Chromosome
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
import kotlin.random.Random

/**
 * Performs a crossover step for population in [PanmicticGA] that modify in-place the input chromosomes.
 *
 * Welcome to use for your own implementations! It is a base function for executing [crossover] step in [PanmicticGA].
 * @param chance chance of crossover between a pair of chromosomes
 * @param parallelismLimit limit of parallel workers
 * @param crossoverType describes the strategy of the crossing stage in [PanmicticGA]
 * @param crossover specific crossing action (How chromosomes will be crossed)
 * @see crossoverType
 */
public suspend inline fun <V, F> PanmicticLifecycle<V, F>.crossover(
    chance: Double,
    parallelismLimit: Int,
    crossoverType: CrossoverType,
    crossinline crossover: suspend (chromosome1: Chromosome<V, F>, chromosome2: Chromosome<V, F>, random: Random) -> Unit,
) {
    when (crossoverType) {
        CrossoverType.Iterative -> iterativeCrossover(chance, parallelismLimit, crossover)
        CrossoverType.Randomly -> randomlyCrossover(chance, parallelismLimit, crossover)
    }
}
