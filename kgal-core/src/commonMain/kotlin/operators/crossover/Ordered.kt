package kgal.operators.crossover

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Executes an ordered crossover (OX) on the input chromosome values. The two value are modified in place.
 * This crossover expects [Chromosome.value] as IntArray.
 * Mixes indexes without conflicts, preserving parental relationships.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 */
public fun crossoverOrdered(value1: IntArray, value2: IntArray, random: Random) {
    val size = minOf(value1.size, value2.size)
    val a = random.nextInt(size)
    val b = random.nextInt(size - 1)
    crossoverOrdered(value1, value2, a, b, size)
}

/**
 * Executes an ordered crossover (OX) on the input chromosome values. The two value are modified in place.
 * This crossover expects [Chromosome.value] as IntArray.
 * Mixes indexes without conflicts, preserving parental relationships.
 * @param value1 value of first child chromosome
 * @param value2 value of second child chromosome
 * @param point1 first point of crossover
 * @param point2 second point of crossover
 * @param size count genes in swap (minimal value between sizes)
 */
public fun crossoverOrdered(
    value1: IntArray,
    value2: IntArray,
    point1: Int,
    point2: Int,
    size: Int = minOf(value1.size, value2.size),
) {
    var a = point1
    var b = point2
    if (a == b) b++
    if (b < a) {
        val temp = a
        a = b
        b = temp
    }

    val holes1 = BooleanArray(size) { true }
    val holes2 = BooleanArray(size) { true }

    for (i in 0..<size) {
        if (i < a || i > b) {
            holes1[value2[i]] = false
            holes2[value1[i]] = false
        }
    }

    val temp1 = value1.copyOf()
    val temp2 = value2.copyOf()
    var k1 = b + 1
    var k2 = b + 1

    for (i in 0..<size) {
        if (!holes1[temp1[(i + b + 1) % size]]) {
            value1[k1 % size] = temp1[(i + b + 1) % size]
            k1++
        }

        if (!holes2[temp2[(i + b + 1) % size]]) {
            value2[k2 % size] = temp2[(i + b + 1) % size]
            k2++
        }
    }

    for (i in a..b) {
        val temp = value1[i]
        value1[i] = value2[i]
        value2[i] = temp
    }
}
