package panmictic

import kgal.chromosome.base.booleans
import kgal.operators.stopBy
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.panmictic.pGA
import kgal.panmictic.population
import kgal.reset
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.measureTime

// Constants (can be changed)
private const val POPULATION_SIZE = 200
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42
private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01
private const val MAX_ITERATION = 50

/**
OneMax task: get the target array of trues [true, true, true, ... , true] using Genetic Algorithm

Chromosome value: BooleanArray = [true, false, true, ... , false]

Chromosome fitness: Int = count of trues in chromosome value

Target: fitness == value.size

Init Population contains of randomly created BooleanArray:
```
[
[true, false, true, ... , true],
[true, true, false, ... , true],
[false, false, true, ... , false],
...
[true, false, true, ... , false],
]
```

Solution:

Get target using genetic operators (selection, crossover, mutation, evaluation)
 */
private fun main() { // Run it!
    val pga = pGA(
        // create population of booleans chromosomes
        population = population(size = POPULATION_SIZE) { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } }, // fitness function for evaluation
    ) {
        random = Random(seed = RANDOM_SEED)
        elitism = ELITISM

        evolve {
            selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
            cxOnePoint(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
            mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE) // mutation ('mut' prefix)
            evaluation()
            println("Iteration $iteration: best fitness = $bestFitness") // Or use stat(bestFitness())
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE } // stop condition
        }
    }
    pga.startBlocking() // start on Main Thread

    println("Result fitness: ${pga.bestFitness}")
    println("Result chromosome: ${pga.best?.value?.joinToString()}")
}

private fun loadTest() {
    val pga = pGA(
        population = population(size = POPULATION_SIZE) { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } },
    ) {
        random = Random(seed = RANDOM_SEED)
        elitism = ELITISM

        evolve {
            selTournament(size = TOURNAMENT_SIZE)
            cxOnePoint(chance = CROSSOVER_CHANCE)
            mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE)
            evaluation()
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE }
        }
    }

    val times = 10
    var time: Long = 0
    repeat(times) { index ->
        runBlocking {
            time += measureTime { pga.start() }
                .inWholeMilliseconds
        }
        println("For index: $index --- iteration: ${pga.iteration} --- ${pga.bestFitness}")
        pga.population.reset(Random(seed = index))
    }
    println("Average time taken: ${time.toDouble() / times} milliseconds")
}
