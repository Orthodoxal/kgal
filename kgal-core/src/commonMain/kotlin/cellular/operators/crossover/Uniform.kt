package kgal.cellular.operators.crossover

import kgal.cellular.CellEvolveScope
import kgal.operators.crossover.crossoverUniform
import kotlin.jvm.JvmName

/**
 * Executes a uniform crossover that modify in-place the input chromosomes.
 * The attributes are swapped according to the [chanceUniform] probability.
 * @param chance chance of crossover between a pair of chromosomes
 * @param chanceUniform probability for index swapping
 */
@JvmName("crossoverUniformArray")
public fun <T, F> CellEvolveScope<Array<T>, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<BooleanArray, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<ByteArray, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<CharArray, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<DoubleArray, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<FloatArray, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<IntArray, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<LongArray, F>.crossoverUniform(
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
public fun <T, F> CellEvolveScope<MutableList<T>, F>.crossoverUniform(
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
public fun <F> CellEvolveScope<ShortArray, F>.crossoverUniform(
    chance: Double,
    chanceUniform: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverUniform(chromosome1.value, chromosome2.value, chanceUniform, random)
}
