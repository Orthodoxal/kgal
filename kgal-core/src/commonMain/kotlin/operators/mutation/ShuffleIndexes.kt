package kgal.operators.mutation

import kgal.utils.moreOrEquals
import kotlin.random.Random

/**
 * Executes a shuffle the attributes of the input chromosome.
 * Usually this mutation is applied on vector of indices.
 *
 * Example:
 * ```
 * Before mutation: 0, 1, 2, 3, 4
 * After mutation: 1, 4, 0, 3, 2 // genes change only position
 * ```
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun <T> mutationShuffleIndexes(
    value: Array<T>,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: BooleanArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: ByteArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: CharArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: DoubleArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: FloatArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: IntArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: LongArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun <T> mutationShuffleIndexes(
    value: MutableList<T>,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
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
 * @param value chromosome to be mutated
 * @param chance the probability of each attribute to be moved.
 */
public fun mutationShuffleIndexes(
    value: ShortArray,
    chance: Double,
    random: Random,
): Unit = shuffleIndexesHelper(value.size, chance, random, { value.shuffle(random) }) { swapIndex, currentIndex ->
    val temp = value[currentIndex]
    value[currentIndex] = value[swapIndex]
    value[swapIndex] = temp
}

/**
 * Helper to shuffle indices.
 * @param mutateAll - executes full shuffle with [shuffle]
 */
private inline fun shuffleIndexesHelper(
    size: Int,
    chance: Double,
    random: Random,
    mutateAll: () -> Unit,
    mutateSwapper: (swapIndex: Int, currentIndex: Int) -> Unit,
) {
    if (chance moreOrEquals 1.0) {
        mutateAll()
    } else {
        for (i in 0..<size) {
            var swapIndex = random.nextInt(0, size - 2)
            if (swapIndex == i) {
                swapIndex += 1
            }
            mutateSwapper(swapIndex, i)
        }
    }
}
