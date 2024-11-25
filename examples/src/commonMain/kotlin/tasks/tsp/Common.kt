package tasks.tsp

import kgal.utils.loop
import kotlin.random.Random

internal typealias Graph = Array<DoubleArray>

internal val Graph.vertices get() = size

internal fun Graph.print() = this.forEach { println(it.joinToString()) }

internal fun graphGenerator(vertexCount: Int, random: Random, from: Double, to: Double, isSymmetric: Boolean): Graph =
    if (isSymmetric) {
        val graph = Array(vertexCount) { DoubleArray(vertexCount) }
        loop(0, vertexCount) { i ->
            loop(i, vertexCount) { j ->
                if (i == j) {
                    graph[i][j] = -1.0
                } else {
                    graph[i][j] = random.nextDouble(from, to)
                    graph[j][i] = graph[i][j]
                }
            }
        }
        graph
    } else {
        Array(vertexCount) { i -> DoubleArray(vertexCount) { j -> if (i == j) -1.0 else random.nextDouble(from, to) } }
    }

internal fun tspFitnessFunction(graph: Graph, path: IntArray): Double {
    var cost = 0.0
    for (i in 1..<graph.vertices) {
        cost += graph[path[i - 1]][path[i]]
    }
    cost += graph[path.last()][path.first()]
    return cost
}

private fun MutableMap<Int, MutableSet<Int>>.copyEdgeMap() =
    this.toMutableMap().apply {
        forEach { set(it.key, it.value.toMutableSet()) }
    }

private fun MutableMap<Int, MutableSet<Int>>.addOrInsert(index: Int, first: Int, second: Int) =
    get(index)?.let { it.add(first); it.add(second) } ?: set(index, mutableSetOf(first, second))

private fun edgeMap(path1: IntArray, path2: IntArray): MutableMap<Int, MutableSet<Int>> {
    val map = mutableMapOf<Int, MutableSet<Int>>()
    map.addOrInsert(path1.first(), first = path1[1], second = path1.last())
    map.addOrInsert(path2.first(), first = path2[1], second = path2.last())
    for (i in 1..<path1.lastIndex) {
        map.addOrInsert(path1[i], first = path1[i - 1], second = path1[i + 1])
        map.addOrInsert(path2[i], first = path2[i - 1], second = path2[i + 1])
    }
    map.addOrInsert(path1.last(), first = path1[path1.lastIndex - 1], second = path1.first())
    map.addOrInsert(path2.last(), first = path2[path1.lastIndex - 1], second = path2.first())
    return map
}

private fun MutableMap<Int, MutableSet<Int>>.createChild(parent: IntArray, random: Random) {
    var city = minBy { it.value.size }.key
    repeat(parent.size) { i ->
        parent[i] = city
        val availableCities = remove(city)
        forEach { it.value.remove(city) }
        city = availableCities?.randomOrNull(random) ?: if (isEmpty()) 0 else keys.first()
    }
}

internal fun crossoverER(value1: IntArray, value2: IntArray, random: Random) {
    val edgeMap1 = edgeMap(value1, value2)
    val edgeMap2 = edgeMap1.copyEdgeMap()
    edgeMap1.createChild(value1, random)
    edgeMap2.createChild(value2, random)
}

internal fun mutationDisplacement(value: IntArray, random: Random) {
    val size = value.size
    var indexStart = random.nextInt(1, value.lastIndex)
    val count = random.nextInt(1, size / 2)
    val buffer = IntArray(count) { value[(indexStart + it) % size] }
    repeat(count) {
        value[indexStart % size] = value[(indexStart + count) % size]
        indexStart++
    }
    repeat(count) {
        value[indexStart % size] = buffer[it]
        indexStart++
    }
}
