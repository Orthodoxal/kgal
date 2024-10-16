package kgal.panmictic.operators.selection

import kgal.chromosome.Chromosome
import kgal.copyOfRange
import kgal.panmictic.PanmicticLifecycle
import kgal.utils.PriorityQueue

/**
 * Special comparator for elitism, contains a pair of chromosome's index in population and chromosome
 */
private val comparator = Comparator<Pair<Int, Chromosome<*, *>>> { p1, p2 -> compareValues(p1.second, p2.second) }

/**
 * Calculates indices of the best chromosomes of the population.
 */
public fun <V, F> elitChromosomeIndices(
    source: Array<Chromosome<V, F>>,
    count: Int,
): IntArray {
    if (count <= 0) error("Count must be more than zero")
    if (source.size < count) error("Count must be less or equal to source size")

    val priority = PriorityQueue(count + 1, comparator)
    source.forEachIndexed { index, chromosome ->
        priority.offer(index to chromosome)
        if (priority.size > count) {
            priority.poll()
        }
    }
    return IntArray(count) { priority.poll()!!.first }
}

/**
 * Calculates the best chromosomes of the population and moves them to the beginning of the population.
 */
public fun <V, F> PanmicticLifecycle<V, F>.recalculateEliteChromosomes() {
    val elitIndices = elitChromosomeIndices(population.get(), elitism)
    val elitChromosomes = Array(elitism) { index -> population[elitIndices[elitism - 1 - index]] }
    val elitOld = population.copyOfRange(fromIndex = 0, toIndex = elitism)
    val replaceIndicesList = mutableListOf<Int>()
    val replaceIndicesOldElitList = mutableListOf<Int>()
    elitIndices.forEachIndexed { index, elitIndex ->
        if (elitIndex >= elitism) {
            replaceIndicesList.add(elitIndex)
        }
        if (index !in elitIndices) {
            replaceIndicesOldElitList.add(index)
        }
        population[index] = elitChromosomes[index]
    }
    replaceIndicesList.forEachIndexed { index, replaceIndex ->
        population[replaceIndex] = elitOld[replaceIndicesOldElitList[index]]
    }
}
