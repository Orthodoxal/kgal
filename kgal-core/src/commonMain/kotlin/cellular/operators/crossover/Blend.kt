package kgal.cellular.operators.crossover

import kgal.cellular.CellEvolveScope
import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverBlend
import kotlin.jvm.JvmName

/**
 * Executes a blend crossover that modify in-place the input chromosomes.
 * The blend crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param alpha Extent of the interval in which the new values can be drawn for each attribute on both side of the parents’ attributes.
 */
@JvmName("cxBlendDoubleArray")
public fun <F> CellEvolveScope<DoubleArray, F>.cxBlend(
    chance: Double,
    alpha: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverBlend(chromosome1.value, chromosome2.value, alpha, random)
}

/**
 * Executes a blend crossover that modify in-place the input chromosomes.
 * The blend crossover expects [Chromosome.value] of floating point numbers.
 * @param chance chance of crossover between a pair of chromosomes
 * @param alpha Extent of the interval in which the new values can be drawn for each attribute on both side of the parents’ attributes.
 */
@JvmName("cxBlendFloatArray")
public fun <F> CellEvolveScope<FloatArray, F>.cxBlend(
    chance: Double,
    alpha: Float,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverBlend(chromosome1.value, chromosome2.value, alpha, random)
}
