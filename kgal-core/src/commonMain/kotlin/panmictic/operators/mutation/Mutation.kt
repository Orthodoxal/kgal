package kgal.panmictic.operators.mutation

import kgal.chromosome.Chromosome
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
import kgal.processor.process
import kgal.size
import kgal.utils.randomByChance
import kotlin.random.Random

/**
 * Performs a mutation step for population in [PanmicticGA] that modify in-place the input chromosomes.
 *
 * Welcome to use for your own implementations! It is a base function for executing [mutation] step in [PanmicticGA].
 * @param chance chance of mutation for each chromosome
 * @param parallelismLimit limit of parallel workers
 * @param mutation specific mutation action (How chromosomes will be mutated)
 */
public suspend inline fun <V, F> PanmicticLifecycle<V, F>.mutation(
    chance: Double,
    parallelismLimit: Int,
    crossinline mutation: (chromosome: Chromosome<V, F>, random: Random) -> Unit,
) {
    process(
        parallelismLimit = parallelismLimit,
        startIteration = elitism,
        endIteration = size,
        action = { index, random ->
            randomByChance(chance, random) {
                mutation(population[index], random)
            }
        },
    )
}
