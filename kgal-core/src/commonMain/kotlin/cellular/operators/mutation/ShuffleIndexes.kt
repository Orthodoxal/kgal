package kgal.cellular.operators.mutation

import kgal.cellular.CellEvolveScope
import kgal.operators.mutation.mutationShuffleIndexes
import kotlin.jvm.JvmName

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesArray")
public fun <T, F> CellEvolveScope<Array<T>, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesBooleanArray")
public fun <F> CellEvolveScope<BooleanArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesByteArray")
public fun <F> CellEvolveScope<ByteArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesCharArray")
public fun <F> CellEvolveScope<CharArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesDoubleArray")
public fun <F> CellEvolveScope<DoubleArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesFloatArray")
public fun <F> CellEvolveScope<FloatArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesIntArray")
public fun <F> CellEvolveScope<IntArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesLongArray")
public fun <F> CellEvolveScope<LongArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesMutableList")
public fun <T, F> CellEvolveScope<MutableList<T>, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param shuffleIndexesChance the probability of each attribute to be moved.
 */
@JvmName("mutShuffleIndexesShortArray")
public fun <F> CellEvolveScope<ShortArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}
