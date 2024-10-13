package kgal.operators.selection

import kgal.chromosome.Chromosome

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

/**
 * PriorityQueue implementation
 */
private class PriorityQueue<T>(
    initialCapacity: Int,
    private val comparator: Comparator<T>,
) {
    private val elements: MutableList<T> = ArrayList(initialCapacity)

    val isEmpty: Boolean
        get() = elements.isEmpty()

    val size: Int get() = elements.size

    fun offer(element: T) {
        elements.add(element)
        siftUp(elements.size - 1)
    }

    fun poll(): T? {
        if (isEmpty) return null
        val root = elements[0]
        elements[0] = elements[elements.size - 1]
        elements.removeAt(elements.size - 1)
        siftDown(0)
        return root
    }

    fun peek(): T? = if (isEmpty) null else elements[0]

    private fun siftUp(index: Int) {
        var childIndex = index
        val child = elements[childIndex]
        var parentIndex = (childIndex - 1) / 2
        while (childIndex > 0 && comparator.compare(child, elements[parentIndex]) < 0) {
            elements[childIndex] = elements[parentIndex]
            childIndex = parentIndex
            parentIndex = (childIndex - 1) / 2
        }
        elements[childIndex] = child
    }

    private fun siftDown(index: Int) {
        var parentIndex = index
        val parent = elements[parentIndex]
        val half = elements.size / 2

        while (parentIndex < half) {
            var childIndex = 2 * parentIndex + 1
            var child = elements[childIndex]
            val rightIndex = childIndex + 1

            if (rightIndex < elements.size && comparator.compare(elements[rightIndex], child) < 0) {
                childIndex = rightIndex
                child = elements[childIndex]
            }

            if (comparator.compare(parent, child) <= 0) {
                break
            }

            elements[parentIndex] = child
            parentIndex = childIndex
        }

        elements[parentIndex] = parent
    }
}
