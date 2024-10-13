package kgal.operators.mutation

import kgal.utils.randomByChance
import kotlin.random.Random

/**
 * Flip the value of the attributes of the input chromosome.
 * The chromosome is expected to be a [BooleanArray]. This mutation is usually applied on boolean individuals.
 *
 * Example:
 * ```
 * Before mutation: 1010011
 * After mutation: 1001101
 * ```
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be flipped.
 */
public fun mutationFlipBit(value: BooleanArray, chance: Double, random: Random): Unit =
    value.forEachIndexed { index, gene ->
        randomByChance(chance, random) { value[index] = !gene }
    }

/**
 * Flip the value of the attributes of the input chromosome.
 * The chromosome is expected to be a [IntArray] with only 1 or 0 values.
 * For other values, correct behavior is not guaranteed.
 * Note! This mutation is usually applied on [BooleanArray].
 *
 * Example:
 * ```
 * Before mutation: 1010011
 * After mutation: 1001101
 * ```
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be flipped.
 */
public fun mutationFlipBit(value: IntArray, chance: Double, random: Random): Unit =
    value.forEachIndexed { index, gene ->
        randomByChance(chance, random) { value[index] = if (gene == 1) 0 else 1 }
    }
