package tasks.tsp

import kgal.chromosome.base.ChromosomeIntArray
import kgal.operators.stopBy
import kgal.panmictic.*
import kgal.panmictic.operators.crossover.CrossoverType
import kgal.panmictic.operators.crossover.crossover
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutation
import kgal.panmictic.operators.selection.selTournament
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kgal.utils.equalsDelta
import kotlin.random.Random

// Constants (can be changed)

private const val POPULATION_SIZE = 512

private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val MAX_ITERATION = 50

private suspend fun <F> PanmicticEvolveScope<IntArray, F>.cxER(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
    crossoverType: CrossoverType = CrossoverType.Iterative,
) {
    crossover(chance, parallelismLimit, crossoverType) { chromosome1, chromosome2, random ->
        crossoverER(chromosome1.value, chromosome2.value, random)
    }
}

private suspend fun <F> PanmicticEvolveScope<IntArray, F>.mutDisplacement(
    chance: Double,
    parallelismLimit: Int = parallelismConfig.workersCount,
) {
    mutation(chance, parallelismLimit) { chromosome, random ->
        mutationDisplacement(chromosome.value, random)
    }
}

/**
 * Resolving Travelling Salesman Problem by [PanmicticGA] (Classical GA).
 */
internal fun tspPanmicticGA(graph: Graph, random: Random, optimal: Double) { // Run it!
    val verticesArray = IntArray(graph.vertices) { it }

    val pga = pGA(
        // create population of ints chromosomes
        population = population(size = POPULATION_SIZE) {
            ChromosomeIntArray(value = verticesArray.apply { shuffle(this@population) }.copyOf())
        },
        fitnessFunction = { value -> -tspFitnessFunction(graph, value) }, // fitness function for evaluation stage
    ) {
        this.random = random // set random generator
        elitism = ELITISM // enable elitism

        // create panmictic evolution strategy
        evolve {
            selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
            cxER(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
            mutDisplacement(chance = MUTATION_CHANCE) // mutation ('mut' prefix)
            evaluation() // evaluate all offspring
            println("Panmictic iteration $iteration: best fitness = $bestFitness") // Or use stat(bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { bestFitness!!.equalsDelta(-optimal) } // stop condition
        }
    }
    pga.startBlocking() // start on Main Thread

    println("Result fitness: ${pga.bestFitness}")
    println("Result chromosome: ${pga.best?.value?.joinToString()}")
}
