package kgal.utils

import kgal.chromosome.Chromosome

/**
 * Fill an [array] with chromosomes of a [subArray].
 * If [subArray] size less than [array] size, it will be filled with clones.
 */
internal fun <V, F> fillArrayChromosomeBySubArray(
    array: Array<Chromosome<V, F>>,
    subArray: Array<Chromosome<V, F>>
) {
    repeat(array.size) { index ->
        if (index < subArray.size) {
            array[index] = subArray[index]
        } else {
            array[index] = subArray[index % subArray.size].clone()
        }
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
