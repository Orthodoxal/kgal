package kgal.utils

/**
 * PriorityQueue implementation
 */
internal class PriorityQueue<T>(
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
