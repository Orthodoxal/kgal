package kgal.cellular.operators.mutation

import kgal.cellular.CellLifecycle
import kgal.operators.mutation.mutationFlipBit
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
 */
@JvmName("mutFlipBitBooleanArray")
public fun <F> CellLifecycle<BooleanArray, F>.mutFlipBit(
    chance: Double,
    flipBitChance: Double,
): Unit = mutation(chance) { chromosome ->
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
 */
@JvmName("mutFlipBitIntArray")
public fun <F> CellLifecycle<IntArray, F>.mutFlipBit(
    chance: Double,
    flipBitChance: Double,
): Unit = mutation(chance) { chromosome ->
    mutationFlipBit(chromosome.value, flipBitChance, random)
}
