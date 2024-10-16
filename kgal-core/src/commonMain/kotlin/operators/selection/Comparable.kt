package kgal.operators.selection

import kgal.chromosome.Chromosome
import kgal.utils.PriorityQueue

/**
 * Select best chromosomes from [source]
 * @param count number of selected best values
 * @return array of the best chromosomes with size [count]
 */
public fun <V, F> selectionBest(
    source: Array<Chromosome<V, F>>,
    count: Int,
): Array<Chromosome<V, F>> = selectionComparable(source, count, compareBy())

/**
 * Select worst chromosomes from [source]
 * @param count number of selected worst values
 * @return array of the worst chromosomes with size [count]
 */
public fun <V, F> selectionWorst(
    source: Array<Chromosome<V, F>>,
    count: Int,
): Array<Chromosome<V, F>> = selectionComparable(source, count, compareByDescending { it })

/**
 * Select chromosome from [source] with [comparator]
 * @return array selected chromosomes with size [count]
 */
public fun <V, F> selectionComparable(
    source: Array<Chromosome<V, F>>,
    count: Int,
    comparator: Comparator<Chromosome<V, F>>,
): Array<Chromosome<V, F>> {
    if (count <= 0) error("Count must be more than zero")
    if (source.size < count) error("Count must be less or equal to source size")

    val priority = PriorityQueue(count + 1, comparator)
    for (chromosome in source) {
        priority.offer(chromosome)
        if (priority.size > count) {
            priority.poll()
        }
    }
    return Array(count) { priority.poll()!!.clone() }
}
