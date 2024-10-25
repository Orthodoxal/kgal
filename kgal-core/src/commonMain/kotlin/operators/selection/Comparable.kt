package kgal.operators.selection

import kgal.chromosome.Chromosome
import kgal.utils.findOrderStatistic

/**
 * Find and moves the best chromosomes to the beginning of source. (Not sort source)
 * @param count number of the best values to be moved to the beginning
 * @return the same source with moved to the beginning chromosomes
 */
public fun <V, F> Array<Chromosome<V, F>>.selectionBest(
    count: Int,
    from: Int,
    to: Int,
): Array<Chromosome<V, F>> = selectionComparable(count, from, to, ascending = false)

/**
 * Find and moves the worst chromosomes to the beginning of source. (Not sort source)
 * @param count number of the worst values to be moved to the beginning
 * @return the same source with moved to the beginning chromosomes
 */
public fun <V, F> Array<Chromosome<V, F>>.selectionWorst(
    count: Int,
    from: Int,
    to: Int,
): Array<Chromosome<V, F>> = selectionComparable(count, from, to, ascending = true)

/**
 * Find and moves chromosomes in ascending or descending order with [findOrderStatistic]. (Not sorted)
 * @see findOrderStatistic
 * @return the same source with moved to the beginning chromosomes
 */
private inline fun <V, F> Array<Chromosome<V, F>>.selectionComparable(
    count: Int,
    from: Int,
    to: Int,
    ascending: Boolean,
): Array<Chromosome<V, F>> {
    if (count <= 0) error("Count must be more than zero")
    if (size < count) error("Count must be less or equal to source size")
    findOrderStatistic(k = count, from = from, to = to, ascending = ascending)
    return this
}
