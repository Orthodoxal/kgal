package tasks.tsp

import kgal.cellular.*
import kgal.cellular.neighborhood.VonNeumann
import kgal.cellular.operators.crossover.crossover
import kgal.cellular.operators.evaluation
import kgal.cellular.operators.evolveCells
import kgal.cellular.operators.mutation.mutation
import kgal.cellular.operators.selection.selTournament
import kgal.chromosome.base.ChromosomeIntArray
import kgal.operators.stopBy
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kgal.utils.equalsDelta
import kotlin.random.Random

// Constants (can be changed)
private const val DIMEN_SIZE = 6
private val DIMENS = Dimens.cube(length = DIMEN_SIZE)
private const val ELITISM = true
private val CELLULAR_TYPE = CellularType.Synchronous
private val CELLULAR_NEIGHBORHOOD = VonNeumann(radius = 2)
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val MAX_ITERATION = 50

private fun <F> CellEvolveScope<IntArray, F>.cxER(
    chance: Double,
) {
    crossover(chance) { chromosome1, chromosome2 ->
        crossoverER(chromosome1.value, chromosome2.value, random)
    }
}

private fun <F> CellEvolveScope<IntArray, F>.mutDisplacement(
    chance: Double,
) {
    mutation(chance) { chromosome ->
        mutationDisplacement(chromosome.value, random)
    }
}

/**
 * Resolving Travelling Salesman Problem by [CellularGA].
 */
internal fun tspCellularGA(graph: Graph, random: Random, optimal: Double) { // Run it!
    val verticesArray = IntArray(graph.vertices) { it }

    val cga = cGA(
        // create population of ints chromosomes (Population size = 6 * 6 * 6 = 216 (cube))
        population = population(dimens = DIMENS) {
            ChromosomeIntArray(value = verticesArray.apply { shuffle(this@population) }.copyOf())
        },
        fitnessFunction = { value -> -tspFitnessFunction(graph, value) }, // fitness function for evaluation stage
    ) {
        this.random = random // set random generator
        elitism = ELITISM // enable elitism
        cellularType = CELLULAR_TYPE // set type of Cellular GA
        neighborhood = CELLULAR_NEIGHBORHOOD // set neighborhood for Cellular Population

        // create cellular evolution strategy
        evolve {
            // Start to evolve all cells of cellular population with their neighborhoods
            // This operator perform N evolutionary strategies on each cell, where N - cells count
            evolveCells {
                selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
                cxER(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
                mutDisplacement(chance = MUTATION_CHANCE) // mutation ('mut' prefix)
                evaluation() // evaluate child
            }

            println("Cellular iteration $iteration: best fitness = $bestFitness") // Or use stat(bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { bestFitness!!.equalsDelta(-optimal) } // stop condition
        }
    }
    cga.startBlocking() // start on Main Thread

    println("Result fitness: ${cga.bestFitness}")
    println("Result chromosome: ${cga.best?.value?.joinToString()}")
}
