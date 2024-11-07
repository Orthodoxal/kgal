package kgal.cellular.operators.mutation

import kgal.cellular.CellLifecycle
import kgal.operators.mutation.mutationUniform
import kotlin.jvm.JvmName

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance chance of mutation between a pair of chromosomes
 * @param uniformChance the probability of each attribute to be moved.
 */
@JvmName("mutUniformDoubleArray")
public fun <F> CellLifecycle<DoubleArray, F>.mutUniform(
    low: Double,
    up: Double,
    chance: Double,
    uniformChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationUniform(chromosome.value, low, up, uniformChance, random)
}

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance chance of mutation between a pair of chromosomes
 * @param uniformChance the probability of each attribute to be moved.
 */
@JvmName("mutUniformIntArray")
public fun <F> CellLifecycle<IntArray, F>.mutUniform(
    low: Int,
    up: Int,
    chance: Double,
    uniformChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationUniform(chromosome.value, low, up, uniformChance, random)
}

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance chance of mutation between a pair of chromosomes
 * @param uniformChance the probability of each attribute to be moved.
 */
@JvmName("mutUniformLongArray")
public fun <F> CellLifecycle<LongArray, F>.mutUniform(
    low: Long,
    up: Long,
    chance: Double,
    uniformChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationUniform(chromosome.value, low, up, uniformChance, random)
}
