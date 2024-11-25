package tasks.tsp

import kgal.utils.equalsDelta
import kotlin.math.min

internal fun tspDynamicProgramming(graph: Graph, startVertex: Int): Pair<Double, List<Int>> {
    val n = graph.size
    val mask = (1 shl n) - 1
    val dp = Array(graph.vertices) { DoubleArray(mask) { -1.0 } }

    fun solve(node: Int, visited: Int): Double {
        if (visited == mask) {
            return graph[node][startVertex]
        }
        if (!dp[node][visited].equalsDelta(-1.0)) {
            return dp[node][visited]
        }

        var minCost = Double.MAX_VALUE
        for (next in 0..<n) {
            if (next != node && (visited and (1 shl next)) == 0) {
                val cost = graph[node][next] + solve(next, visited or (1 shl next))
                minCost = min(minCost, cost)
            }
        }

        dp[node][visited] = minCost
        return minCost
    }

    val minCost = solve(startVertex, 1 shl startVertex)

    val allPoints = buildList { repeat(graph.vertices) { add(it) } }
    val path = mutableListOf(startVertex)
    var mask2 = 1 shl startVertex
    var pos = startVertex
    while (path.size < n) {
        var next = -1
        for (i in allPoints) {
            if (mask2 and (1 shl i) == 0 && (next == -1 || graph[pos][i] + dp[i][mask2 or (1 shl i)] < graph[pos][next] + dp[next][mask2 or (1 shl next)])) {
                next = i
            }
        }
        path.add(next)
        mask2 = mask2 or (1 shl next)
        pos = next
    }
    path.add(startVertex)

    return minCost to path
}
