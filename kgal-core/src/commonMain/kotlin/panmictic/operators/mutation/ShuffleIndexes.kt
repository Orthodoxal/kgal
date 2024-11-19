package kgal.panmictic.operators.mutation

import kgal.operators.mutation.mutationShuffleIndexes
import kgal.panmictic.PanmicticEvolveScope
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesArray")
public suspend fun <T, F> PanmicticEvolveScope<Array<T>, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesBooleanArray")
public suspend fun <F> PanmicticEvolveScope<BooleanArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesByteArray")
public suspend fun <F> PanmicticEvolveScope<ByteArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesCharArray")
public suspend fun <F> PanmicticEvolveScope<CharArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesDoubleArray")
public suspend fun <F> PanmicticEvolveScope<DoubleArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesFloatArray")
public suspend fun <F> PanmicticEvolveScope<FloatArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesIntArray")
public suspend fun <F> PanmicticEvolveScope<IntArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesLongArray")
public suspend fun <F> PanmicticEvolveScope<LongArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesMutableList")
public suspend fun <T, F> PanmicticEvolveScope<MutableList<T>, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
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
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutShuffleIndexesShortArray")
public suspend fun <F> PanmicticEvolveScope<ShortArray, F>.mutShuffleIndexes(
    chance: Double,
    shuffleIndexesChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
    mutationShuffleIndexes(chromosome.value, shuffleIndexesChance, random)
}
