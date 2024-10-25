package kgal.operators.mutation

import kgal.utils.randomByChance
import kotlin.random.Random

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param value chromosome to be mutated
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance the probability of each attribute to be mutated.
 */
public fun mutationUniform(
    value: DoubleArray,
    low: Double,
    up: Double,
    chance: Double,
    random: Random,
) {
    value.indices.forEach { i -> randomByChance(chance, random) { value[i] = random.nextDouble(low, up + 1.0) } }
}

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param value chromosome to be mutated
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance the probability of each attribute to be mutated.
 */
public fun mutationUniform(
    value: IntArray,
    low: Int,
    up: Int,
    chance: Double,
    random: Random,
) {
    value.indices.forEach { i -> randomByChance(chance, random) { value[i] = random.nextInt(low, up + 1) } }
}

/**
 * Executes a uniform mutation for the attributes of the input chromosome.
 * @param value chromosome to be mutated
 * @param low A value that is the lower bound of the uniform distribution (inclusive).
 * @param up A value that is the upper bound of the uniform distribution (inclusive).
 * @param chance the probability of each attribute to be mutated.
 */
public fun mutationUniform(
    value: LongArray,
    low: Long,
    up: Long,
    chance: Double,
    random: Random,
) {
    value.indices.forEach { i -> randomByChance(chance, random) { value[i] = random.nextLong(low, up + 1) } }
}
