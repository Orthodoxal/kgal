package kgal.operators.selection

import kgal.chromosome.Chromosome
import kotlin.jvm.JvmName
import kotlin.random.Random

/**
 * Executes roulette selection. It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param source the population of Chromosomes
 * @param totalFitness the sum of all fitness values in a population
 */
@JvmName("selectionRouletteFitDouble")
public fun <V> selectionRoulette(
    source: Array<Chromosome<V, Double>>,
    totalFitness: Double,
    random: Random,
): Chromosome<V, Double> {
    val randomSum = random.nextDouble(totalFitness)
    var sum = 0.0
    for (chromosome in source) {
        sum += chromosome.fitness ?: error("Fitness is null")
        if (sum >= randomSum) {
            return chromosome.clone()
        }
    }
    error("selectionRoulette critical exception")
}

/**
 * Executes roulette selection. It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param source the population of Chromosomes
 * @param totalFitness the sum of all fitness values in a population
 */
@JvmName("selectionRouletteFitFloat")
public fun <V> selectionRoulette(
    source: Array<Chromosome<V, Float>>,
    totalFitness: Float,
    random: Random,
): Chromosome<V, Float> {
    val randomSum = random.nextFloat() * totalFitness
    var sum = 0f
    for (chromosome in source) {
        sum += chromosome.fitness ?: error("Fitness is null")
        if (sum >= randomSum) {
            return chromosome.clone()
        }
    }
    error("selectionRoulette critical exception")
}

/**
 * Executes roulette selection. It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param source the population of Chromosomes
 * @param totalFitness the sum of all fitness values in a population
 */
@JvmName("selectionRouletteFitInt")
public fun <V> selectionRoulette(
    source: Array<Chromosome<V, Int>>,
    totalFitness: Long,
    random: Random,
): Chromosome<V, Int> = roulette(source, totalFitness, random) { sum, fitness -> sum + fitness }

/**
 * Executes roulette selection. It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param source the population of Chromosomes
 * @param totalFitness the sum of all fitness values in a population
 */
@JvmName("selectionRouletteFitLong")
public fun <V> selectionRoulette(
    source: Array<Chromosome<V, Long>>,
    totalFitness: Long,
    random: Random,
): Chromosome<V, Long> = roulette(source, totalFitness, random) { sum, fitness -> sum + fitness }

/**
 * Executes roulette selection. It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param source the population of Chromosomes
 * @param totalFitness the sum of all fitness values in a population
 */
@JvmName("selectionRouletteFitShort")
public fun <V> selectionRoulette(
    source: Array<Chromosome<V, Short>>,
    totalFitness: Long,
    random: Random,
): Chromosome<V, Short> = roulette(source, totalFitness, random) { sum, fitness -> sum + fitness }

/**
 * Executes roulette selection. It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 * @param source the population of Chromosomes
 * @param totalFitness the sum of all fitness values in a population
 */
@JvmName("selectionRouletteFitByte")
public fun <V> selectionRoulette(
    source: Array<Chromosome<V, Byte>>,
    totalFitness: Long,
    random: Random,
): Chromosome<V, Byte> = roulette(source, totalFitness, random) { sum, fitness -> sum + fitness }

/**
 * Executes roulette selection with [Long] sum.
 */
internal inline fun <V, N> roulette(
    source: Array<Chromosome<V, N>>,
    totalFitness: Long,
    random: Random,
    summator: (sum: Long, fitness: N) -> Long,
): Chromosome<V, N> {
    val randomSum = random.nextLong(totalFitness)
    var sum = 0L
    for (chromosome in source) {
        sum = summator(sum, chromosome.fitness ?: error("Fitness is null"))
        if (sum >= randomSum) {
            return chromosome.clone()
        }
    }
    error("Selection roulette critical exception")
}
