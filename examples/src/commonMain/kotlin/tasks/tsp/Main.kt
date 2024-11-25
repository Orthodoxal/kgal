package tasks.tsp

import kgal.GA
import kotlin.random.Random

private const val IS_SYMMETRIC = true
private const val VERTEX_COUNT = 20
private const val RANDOM_SEED = 42

/**
 * TSP - `Travelling Salesman Problem`
 *
 * Given a list of cities and the distances between each pair of cities,
 * what is the shortest possible route that visits each city exactly once and returns to the origin city?
 *
 * Solving this problem with a genetic algorithm may not achieve the optimal result,
 * but it is capable of finding a solution very close to the optimal one.
 *
 * For comparison, this example uses all available GAs in `kgal` and dynamic programming (`DP`) (to find the optimal solution).
 * Experiment with the number of cities ([VERTEX_COUNT]).
 * Don't be surprised by the out of memory error for `DP` when the number of cities > 26. Also compare times to get results.
 *
 * Also see realization of solution by [GA]:
 * - [Graph] as graph of cities
 * - [tspFitnessFunction]
 * - [crossoverER] - special crossover for `tsp`
 * - [mutationDisplacement] - special mutation for `tsp`
 * @see <a href="https://en.wikipedia.org/wiki/Travelling_salesman_problem">Travelling Salesman Problem</a>
 * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/GLPK_solution_of_a_travelling_salesman_problem.svg/440px-GLPK_solution_of_a_travelling_salesman_problem.svg.png"/>
 */
private fun main() {
    val random = Random(seed = RANDOM_SEED) // random generator
    val graph = graphGenerator(vertexCount = VERTEX_COUNT, random, 1.0, 50.0, isSymmetric = IS_SYMMETRIC)

    val (costDP, pathDP) = tspDynamicProgramming(graph, 0)

    println("DP cost: $costDP")
    println("DP path: ${pathDP.joinToString()}")

    tspPanmicticGA(graph, random, optimal = costDP)
    tspDistributedGA(graph, random, optimal = costDP)
    tspCellularGA(graph, random, optimal = costDP)

    println("Chromosomes can be different but represent the same path, by scrolling it relative to the first index you can get the same one that was received by DP")
    println("Also chromosome's paths may be not optimal cause genetic algorithms do not guarantee optimal results, but can quickly find one close to the optimal one")
}
