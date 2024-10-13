package kgal.operators.crossover

import kgal.utils.randomByChance
import kotlin.random.Random

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun <T> crossoverUniform(value1: Array<T>, value2: Array<T>, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: BooleanArray, value2: BooleanArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: ByteArray, value2: ByteArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: CharArray, value2: CharArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: DoubleArray, value2: DoubleArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: FloatArray, value2: FloatArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: IntArray, value2: IntArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: LongArray, value2: LongArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun <T> crossoverUniform(value1: MutableList<T>, value2: MutableList<T>, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chance] probability.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param chance probability for index swapping
 */
public fun crossoverUniform(value1: ShortArray, value2: ShortArray, chance: Double, random: Random): Unit =
    swapStrategy(value1.size, chance, random) { swapIndex ->
        val temp = value1[swapIndex]
        value1[swapIndex] = value2[swapIndex]
        value2[swapIndex] = temp
    }

/**
 * Executes swap with [swapper] for uniform crossover
 */
private inline fun swapStrategy(size: Int, chance: Double, random: Random, swapper: (swapIndex: Int) -> Unit): Unit =
    repeat(size) { randomByChance(chance, random) { swapper(it) } }
