package kgal.utils

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
