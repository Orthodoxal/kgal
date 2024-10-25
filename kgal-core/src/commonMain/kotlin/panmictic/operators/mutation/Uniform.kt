package kgal.panmictic.operators.mutation

import kgal.operators.mutation.mutationUniform
import kgal.panmictic.PanmicticLifecycle
import kotlin.jvm.JvmName

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance chance of mutation between a pair of chromosomes
 * @param uniformChance the probability of each attribute to be moved.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutUniformDoubleArray")
public suspend fun <F> PanmicticLifecycle<DoubleArray, F>.mutUniform(
    low: Double,
    up: Double,
    chance: Double,
    uniformChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
    mutationUniform(chromosome.value, low, up, uniformChance, random)
}

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance chance of mutation between a pair of chromosomes
 * @param uniformChance the probability of each attribute to be moved.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutUniformIntArray")
public suspend fun <F> PanmicticLifecycle<IntArray, F>.mutUniform(
    low: Int,
    up: Int,
    chance: Double,
    uniformChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
    mutationUniform(chromosome.value, low, up, uniformChance, random)
}

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance chance of mutation between a pair of chromosomes
 * @param uniformChance the probability of each attribute to be moved.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutUniformLongArray")
public suspend fun <F> PanmicticLifecycle<LongArray, F>.mutUniform(
    low: Long,
    up: Long,
    chance: Double,
    uniformChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
    mutationUniform(chromosome.value, low, up, uniformChance, random)
}
