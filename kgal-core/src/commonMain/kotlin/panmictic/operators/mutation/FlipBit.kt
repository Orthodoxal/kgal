package kgal.panmictic.operators.mutation

import kgal.operators.mutation.mutationFlipBit
import kgal.panmictic.PanmicticLifecycle
import kotlin.jvm.JvmName

/**
 * Flip the value of the attributes of the input chromosome.
 * The chromosome is expected to be a [BooleanArray]. This mutation is usually applied on boolean individuals.
 *
 * Example:
 * ```
 * Before mutation: 1010011
 * After mutation: 1001101
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param flipBitChance the probability of each attribute to be flipped.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutFlipBitBooleanArray")
public suspend fun <F> PanmicticLifecycle<BooleanArray, F>.mutFlipBit(
    chance: Double,
    flipBitChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
    mutationFlipBit(chromosome.value, flipBitChance, random)
}

/**
 * Flip the value of the attributes of the input chromosome.
 * The chromosome is expected to be a [BooleanArray]. This mutation is usually applied on boolean individuals.
 *
 * Example:
 * ```
 * Before mutation: 1010011
 * After mutation: 1001101
 * ```
 * @param chance chance of mutation between a pair of chromosomes
 * @param flipBitChance the probability of each attribute to be flipped.
 * @param parallelismLimit limit of parallel workers
 */
@JvmName("mutFlipBitIntArray")
public suspend fun <F> PanmicticLifecycle<IntArray, F>.mutFlipBit(
    chance: Double,
    flipBitChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
): Unit = mutation(chance, parallelismLimit) { chromosome, random ->
    mutationFlipBit(chromosome.value, flipBitChance, random)
}
