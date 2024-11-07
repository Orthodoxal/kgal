package kgal.cellular.operators.crossover

import kgal.cellular.CellLifecycle
import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverOrdered
import kotlin.jvm.JvmName

/**
 * Executes an ordered crossover (OX) on the input chromosome values. The two value are modified in place.
 * This crossover expects [Chromosome.value] as IntArray.
 * Mixes indexes without conflicts, preserving parental relationships.
 * @param chance chance of crossover between a pair of chromosomes
 */
@JvmName("cxOrderedIntArray")
public fun <F> CellLifecycle<IntArray, F>.cxOrdered(
    chance: Double,
): Unit = crossover(chance) { chromosome1, chromosome2 ->
    crossoverOrdered(chromosome1.value, chromosome2.value, random)
}
