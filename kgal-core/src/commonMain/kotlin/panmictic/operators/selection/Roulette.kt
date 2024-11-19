package kgal.panmictic.operators.selection

import kgal.Population
import kgal.chromosome.Chromosome
import kgal.operators.selection.selectionRoulette
import kgal.panmictic.PanmicticLifecycle
import kgal.processor.parallelism.ParallelismConfig
import kotlin.jvm.JvmName

/**
 * Executes roulette selection step for [Population]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("selRouletteFitInt")
public suspend fun <V> PanmicticLifecycle<V, Int>.selRoulette(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    val totalFitness = population.get().fold(0L) { acc: Long, chromosome: Chromosome<V, Int> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selection(parallelismLimit) { source, random -> selectionRoulette(source, totalFitness, random) }
}

/**
 * Executes roulette selection step for [Population]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("selRouletteFitLong")
public suspend fun <V> PanmicticLifecycle<V, Long>.selRoulette(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    val totalFitness = population.get().fold(0L) { acc: Long, chromosome: Chromosome<V, Long> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selection(parallelismLimit) { source, random -> selectionRoulette(source, totalFitness, random) }
}

/**
 * Executes roulette selection step for [Population]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("selRouletteFitShort")
public suspend fun <V> PanmicticLifecycle<V, Short>.selRoulette(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    val totalFitness = population.get().fold(0L) { acc: Long, chromosome: Chromosome<V, Short> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selection(parallelismLimit) { source, random -> selectionRoulette(source, totalFitness, random) }
}

/**
 * Executes roulette selection step for [Population]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("selRouletteFitByte")
public suspend fun <V> PanmicticLifecycle<V, Byte>.selRoulette(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    val totalFitness = population.get().fold(0L) { acc: Long, chromosome: Chromosome<V, Byte> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selection(parallelismLimit) { source, random -> selectionRoulette(source, totalFitness, random) }
}

/**
 * Executes roulette selection step for [Population]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("selRouletteFitDouble")
public suspend fun <V> PanmicticLifecycle<V, Double>.selRoulette(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    val totalFitness = population.get().fold(0.0) { acc: Double, chromosome: Chromosome<V, Double> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selection(parallelismLimit) { source, random -> selectionRoulette(source, totalFitness, random) }
}

/**
 * Executes roulette selection step for [Population]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("selRouletteFitFloat")
public suspend fun <V> PanmicticLifecycle<V, Float>.selRoulette(
    parallelismLimit: Int = ParallelismConfig.NO_PARALLELISM,
) {
    val totalFitness = population.get().fold(0f) { acc: Float, chromosome: Chromosome<V, Float> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selection(parallelismLimit) { source, random -> selectionRoulette(source, totalFitness, random) }
}
