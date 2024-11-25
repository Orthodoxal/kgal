package tasks.tsp

import kgal.chromosome.base.ChromosomeIntArray
import kgal.distributed.*
import kgal.distributed.operators.migration
import kgal.name
import kgal.operators.stopBy
import kgal.panmictic.PanmicticEvolveScope
import kgal.panmictic.evolve
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
private const val DISTRIBUTED_GA_COUNT = 4
private const val DISTRIBUTED_MAX_ITERATION = 5
private const val POPULATION_SIZE = 50
private const val ELITISM = 5
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val MAX_ITERATION = 20

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
 * Resolving Travelling Salesman Problem by [DistributedGA].
 */
internal fun tspDistributedGA(graph: Graph, random: Random, optimal: Double) { // Run it!
    val verticesArray = IntArray(graph.vertices) { it }

    val dga = dGA(
        factory = factory@{
            ChromosomeIntArray(value = verticesArray.apply { shuffle(this@factory) }.copyOf())
        }, // common factory for subpopulations
        fitnessFunction = { value ->
            -tspFitnessFunction(
                graph,
                value
            )
        }, // common fitness function for evaluation stage
    ) {
        this.random = random // set random generator

        // set optimal parallelism configuration
        parallelismConfig {
            // distributed parallelism - run each child GA independently in their own coroutine!
            workersCount = DISTRIBUTED_GA_COUNT
        }

        // add children GAs to distributed configuration
        +pGAs(
            count = DISTRIBUTED_GA_COUNT,
            population = { population(size = POPULATION_SIZE) },
        ) {
            elitism = ELITISM // enable elitism

            // create panmictic evolution strategy
            evolve {
                selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
                cxER(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
                mutDisplacement(chance = MUTATION_CHANCE) // mutation ('mut' prefix)
                evaluation() // evaluate all offspring
                stopBy(maxIteration = MAX_ITERATION) { bestFitness!!.equalsDelta(-optimal) } // stop condition
            }
        }

        // create distributed evolution strategy
        evolve(
            useDefault = true, // (default is true) uses launchChildren (see) operator before evolution function below
        ) {
            println("Distributed iteration $iteration: best fitness = $bestFitness")
            children.forEach { child -> println("${child.name}: best fitness = ${child.bestFitness}") }
            // limit only iterations for dGA not need stop condition by here cause useDefault is true
            stopBy(maxIteration = DISTRIBUTED_MAX_ITERATION)
            migration(percent = 0.1) // set random migration between islands
        }
    }
    dga.startBlocking() // start on Main Thread

    println("Result fitness: ${dga.bestFitness}")
    println("Result chromosome: ${dga.best?.value?.joinToString()}")
}
