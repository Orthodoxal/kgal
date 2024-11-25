package tasks.sssp

import kgal.chromosome.Chromosome
import kgal.operators.crossover.crossoverOrdered
import kgal.operators.mutation.mutationShuffleIndexes
import kgal.operators.stopBy
import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.CrossoverType
import kgal.panmictic.operators.crossover.crossover
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutation
import kgal.panmictic.operators.selection.selTournament
import kgal.panmictic.pGA
import kgal.panmictic.PanmicticGA
import kgal.panmictic.population
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kotlin.random.Random

// Define constants for SSSP (can be changed)
/**
 * SSSP - Single Source Shortest Paths - problem of finding minimal routes in a graph relative to a selected vertex.
 * Can be also solved by `Dijkstra's algorithm`.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Shortest_path_problem#:~:text=The-,single%2Dsource%20shortest%20path%20problem,-%2C%20in%20which%20we">Single Source Shortest Paths</a>
 * @see <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">Dijkstra's algorithm</a>
 */
private const val START_V = 0
private const val INF = 100
private const val VERTEX_COUNT = 6
private val vertexes = intArrayOf(0, 1, 2, 3, 4, 5)
private val PATHS = arrayOf(
    intArrayOf(0, 3, 1, 3, INF, INF),
    intArrayOf(3, 0, 4, INF, INF, INF),
    intArrayOf(1, 4, 0, INF, 7, 5),
    intArrayOf(3, INF, INF, 0, INF, 2),
    intArrayOf(INF, INF, 7, INF, 0, 4),
    intArrayOf(INF, INF, 5, 2, 4, 0),
)
private const val SHORTEST_PATH_LENGTH = 20 // actual only for current PATHS!



/**
 * STEP 1: create custom Chromosome for task
 *
 * For a given task, there may be different chromosome coding options.
 * In this example, the following option was selected:
 * One chromosome represents all possible paths from the starting point (node 1) to all vertices of the graph.
 * [Chromosome.value] is array with `size = VERTEX_COUNT` which contains of [IntArray] also with `size = VERTEX_COUNT` that includes index sequences:
 * ```
 * Array represents shortest paths from target node:
 * 0: 0, 5, 4, 1, 3, 2 // to 0 node: 0 -> 0
 * 1: 1, 4, 3, 5, 2, 0 // to 1 node: 0 -> 1
 * 2: 2, 5, 0, 1, 3, 4 // to 2 node: 0 -> 2
 * 3: 3, 5, 4, 0, 2, 1 // to 3 node: 0 -> 3
 * 4: 2, 4, 5, 1, 3, 0 // to 4 node: 0 -> 4 = 0 -> 2 -> 4
 * 5: 3, 5, 4, 2, 1, 0 // to 5 node: 0 -> 5 = 0 -> 3 -> 5
 * ```
 *
 * [Chromosome.fitness] evaluated for [PATHS] matrix like (see [fitnessSSSP]):
 * ```
 * 0 -> 0 = 0
 * 0 -> 1 = 3
 * 0 -> 2 = 1
 * 0 -> 3 = 3
 * 0 -> 4 = 0 -> 2 -> 4 = 1 + 7 = 8
 * 0 -> 5 = 0 -> 3 -> 5 = 3 + 2 = 5
 *
 * Result = 0 + 3 + 1 + 3 + 8 + 5 = 20 = SHORTEST_PATH_LENGTH
 * ```
 */
private data class ChromosomeSSSP(
    override var value: Array<IntArray>,
    override var fitness: Int? = 0,
) : Chromosome<Array<IntArray>, Int?> {
    override fun clone(): Chromosome<Array<IntArray>, Int?> =
        this.copy(value = Array(value.size) { value[it].copyOf() })

    override fun compareTo(other: Chromosome<Array<IntArray>, Int?>): Int = compareValues(fitness, other.fitness)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChromosomeSSSP

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentDeepEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = value.contentDeepHashCode()
        result = 31 * result + (fitness ?: 0)
        return result
    }
}

/**
 * STEP 2.1: create crossover operator using crossoverOrdered for each path.
 */
private fun crossoverSSSP(value1: Array<IntArray>, value2: Array<IntArray>, random: Random) {
    for (i in value1.indices) crossoverOrdered(value1[i], value2[i], random)
}

/**
 * STEP 2.2: create crossover stage for [PanmicticGA] using [crossoverSSSP] operator and base [crossover] function.
 */
private suspend fun PanmicticEvolveScope<Array<IntArray>, Int?>.cxSSSP(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
) {
    crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
        crossoverSSSP(chromosome1.value, chromosome2.value, random)
    }
}

/**
 * STEP 3.1: create mutation operator using [mutationShuffleIndexes] for each path.
 */
private fun mutationSSSP(value: Array<IntArray>, chance: Double, random: Random) {
    for (path in value) {
        mutationShuffleIndexes(path, chance, random)
    }
}

/**
 * STEP 3.2: create mutation stage using [mutationSSSP] operator and base [mutation] function.
 */
private suspend fun PanmicticEvolveScope<Array<IntArray>, Int?>.mutSSSP(
    chance: Double,
    shuffleChance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
) {
    mutation(chance, parallelismLimit) { chromosome, random ->
        mutationSSSP(chromosome.value, shuffleChance, random)
    }
}

/**
 * STEP 4: create fitness function that evaluates current [value] with [paths] and [startV] (start vertex)
 */
fun fitnessSSSP(startV: Int, paths: Array<IntArray>, value: Array<IntArray>): Int {
    var sum = 0
    for (i in value.indices) {
        val path = value[i]
        var j = 0
        var vertex = startV
        do {
            sum += paths[vertex][path[j]]
            vertex = path[j]
        } while (path[j++] != i)
    }
    return -sum
}

/**
 * Result (target) chromosome for current PATHS:
 * ```
 * 0: 0, 5, 4, 1, 3, 2
 * 1: 1, 4, 3, 5, 2, 0
 * 2: 2, 5, 0, 1, 3, 4
 * 3: 3, 5, 4, 0, 2, 1
 * 4: 2, 4, 5, 1, 3, 0
 * 5: 3, 5, 4, 2, 1, 0
 *
 * 0 -> 0 = 0
 * 0 -> 1 = 3
 * 0 -> 2 = 1
 * 0 -> 3 = 3
 * 0 -> 4 = 0 -> 2 -> 4 = 1 + 7 = 8
 * 0 -> 5 = 0 -> 3 -> 5 = 3 + 2 = 5
 *
 * Result = 0 + 3 + 1 + 3 + 8 + 5 = 20 = SHORTEST_PATH_LENGTH
 * ```
 */

// Genetic params
private const val POPULATION_SIZE = 300
private const val RANDOM_SEED = 42
private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val SHUFFLE_CHANCE = 0.5
private const val MAX_ITERATION = 30

private fun main() { // Run it!

    // STEP 5: create Panmictic GA
    val pga = pGA(
        // create population of random ChromosomeSSSP chromosomes
        population = population(size = POPULATION_SIZE) {
            ChromosomeSSSP(value = Array(VERTEX_COUNT) { vertexes.shuffle(random = this); vertexes.copyOf() })
        },
        fitnessFunction = { value -> fitnessSSSP(START_V, PATHS, value) }, // fitness function for evaluation stage
    ) {
        random = Random(seed = RANDOM_SEED) // set random generator
        elitism = ELITISM // enable elitism

        // create panmictic evolution strategy
        evolve {
            selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
            cxSSSP(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
            mutSSSP(chance = MUTATION_CHANCE, shuffleChance = SHUFFLE_CHANCE) // mutation ('mut' prefix)
            evaluation() // evaluate all offspring
            println("Iteration $iteration: best fitness = $bestFitness") // Or use stat(bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { -bestFitness!! == SHORTEST_PATH_LENGTH } // stop condition
        }
    }
    pga.startBlocking() // start on Main Thread

    println("Result fitness: ${-pga.bestFitness!!}")
    println("Result chromosome:")
    pga.best?.value?.forEachIndexed { index, ints -> println("$index: ${ints.joinToString()}") }
}
