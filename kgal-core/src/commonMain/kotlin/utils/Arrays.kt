package kgal.utils

import kgal.chromosome.Chromosome
import kotlin.random.Random
import kotlin.time.measureTime

/**
 * Fill an array in range [from]..<[to] with chromosomes from range 0..<[from].
 * @param from index from filling starts (inclusive). Must be positive only
 * @param to index filling to (exclusive)
 */
internal fun <V, F> Array<Chromosome<V, F>>.fillWithSameFromSource(
    from: Int,
    to: Int,
) {
    require(from > 0) { "from must be positive only. fillWithSameFromSource - fills from range 0..<from" }
    loop(start = from, end = to) { index ->
        this[index] = this[index % from].clone()
    }
}

/**
 * For each implementation with start and end.
 * @param start starting index
 * @param end ending index
 */
public inline fun <T> Array<out T>.forEach(start: Int, end: Int = this.size, action: (T) -> Unit) {
    var index = start
    while (index < end) {
        action(this[index])
        index++
    }
}

/**
 * For each indexed implementation with start and end.
 * @param start starting index
 * @param end ending index
 */
public inline fun <T> Array<out T>.forEachIndexed(start: Int, end: Int = this.size, action: (index: Int, T) -> Unit) {
    var index = start
    while (index < end) {
        action(index, this[index])
        index++
    }
}

/**
 * Implementation standard loop: for(int i = [start]; i < [end]; i++)
 * @param start starting index
 * @param end ending index
 */
public inline fun loop(start: Int, end: Int, action: (index: Int) -> Unit) {
    var index = start
    while (index < end) {
        action(index)
        ++index
    }
}

/**
 * Implementation standard loop: for(int i = [start]; i < [end]; i += [step])
 * @param start starting index
 * @param end ending index
 * @param step step iteration
 */
public inline fun loop(start: Int, end: Int, step: Int, action: (index: Int) -> Unit) {
    var index = start
    while (index < end) {
        action(index)
        index += step
    }
}

/**
 * For each reversed implementation with from and to.
 * @param from starting index
 * @param to ending index
 */
public inline fun <T> Array<T>.forEachReverse(
    from: Int = lastIndex,
    to: Int = 0,
    action: (T) -> Unit,
) {
    var index = from
    while (index >= to) {
        action(this[index])
        index--
    }
}

/**
 * For each reversed indexed implementation with from and to.
 * @param from starting index
 * @param to ending index
 */
public inline fun <T> Array<T>.forEachReverseIndexed(
    from: Int = lastIndex,
    to: Int = 0,
    action: (T, index: Int) -> Unit,
) {
    var index = from
    while (index >= to) {
        action(this[index], index)
        index--
    }
}

/**
 * For each reversed indexed implementation with from and to.
 * @param from starting index
 * @param to ending index
 */
public inline fun IntArray.forEachReverseIndexed(
    from: Int = lastIndex,
    to: Int = 0,
    action: (Int, index: Int) -> Unit,
) {
    var index = from
    while (index >= to) {
        action(this[index], index)
        index--
    }
}

/**
 * Executes splits array with standard partition (that used in quicksort) in ascending order
 * @param start index from (inclusive)
 * @param end index to (inclusive)
 * @return index of split (to the left, elements are less than or equal, to the right, elements are greater)
 */
public fun <T : Comparable<T>> Array<T>.partitionAscending(start: Int, end: Int): Int {
    val pivot = get(end)
    var i = start

    loop(start = start, end = end) { j ->
        // If the current element is less than or equal to the pivot
        if (get(j) <= pivot) {
            // Swap the current element with the element at the temporary pivot index
            swap(first = i, second = j)
            // Move the temporary pivot index forward
            ++i
        }
    }

    // Swap the pivot with the last element
    swap(i, end)
    return i // the pivot index
}

/**
 * Executes splits array with standard partition (that used in quicksort) in descending order
 * @param start index from (inclusive)
 * @param end index to (inclusive)
 * @return index of split (to the left, elements are less than or equal, to the right, elements are greater)
 */
public fun <T : Comparable<T>> Array<T>.partitionDescending(start: Int, end: Int): Int {
    val pivot = get(end)
    var i = start

    loop(start = start, end = end) { j ->
        // If the current element is less than or equal to the pivot
        if (get(j) >= pivot) {
            // Swap the current element with the element at the temporary pivot index
            swap(first = i, second = j)
            // Move the temporary pivot index forward
            ++i
        }
    }

    // Swap the pivot with the last element
    swap(i, end)
    return i // the pivot index
}

/**
 * Executes swap between two indices.
 */
public inline fun <T> Array<T>.swap(first: Int, second: Int) {
    val temp = this[first]
    this[first] = this[second]
    this[second] = temp
}

/**
 * Executes K-th order statistic. Average O(N).
 * Swap elements in array. At the end, the K-th element will be under index [k].
 * @param k element search ordinal number. 0 < [k] <= [to] - [from]
 * @param from index from for search in array (inclusive)
 * @param to index to for search in array (exclusive)
 * @param ascending if true search in ascending order else in descending order
 * @return k-th element from array
 */
public fun <T : Comparable<T>> Array<T>.findOrderStatistic(
    k: Int,
    from: Int,
    to: Int,
    ascending: Boolean,
): T {
    val actualK = k - 1
    var left = from
    var right = to - 1

    while (true) {
        val mid = if (ascending) partitionAscending(left, right) else partitionDescending(left, right)

        when {
            mid == actualK -> return get(mid)
            actualK < mid -> right = mid - 1
            else -> left = mid + 1
        }
    }
}
