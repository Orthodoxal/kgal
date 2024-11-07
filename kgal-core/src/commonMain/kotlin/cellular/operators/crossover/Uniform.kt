package kgal.cellular.operators.crossover

import kgal.cellular.CellLifecycle
import kgal.operators.crossover.crossoverUniform
import kotlin.jvm.JvmName

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformArray")
public fun <T, F> CellLifecycle<Array<T>, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformBooleanArray")
public fun <F> CellLifecycle<BooleanArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformByteArray")
public fun <F> CellLifecycle<ByteArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformCharArray")
public fun <F> CellLifecycle<CharArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformDoubleArray")
public fun <F> CellLifecycle<DoubleArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformFloatArray")
public fun <F> CellLifecycle<FloatArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformIntArray")
public fun <F> CellLifecycle<IntArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformLongArray")
public fun <F> CellLifecycle<LongArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformMutableList")
public fun <T, F> CellLifecycle<MutableList<T>, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformShortArray")
public fun <F> CellLifecycle<ShortArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}
