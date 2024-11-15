package distribution

import kgal.chromosome.base.booleans
import kgal.distributed.dGA
import kgal.distributed.evolve
import kgal.distributed.operators.migration
import kgal.distributed.pGAs
import kgal.distributed.population
import kgal.name
import kgal.operators.stopBy
import kgal.panmictic.evolve
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.parallelismConfig
import kgal.startBlocking
import kgal.statistics.stats.best
import kgal.statistics.stats.bestFitness
import kotlin.random.Random

// Constants (can be changed)
private const val DISTRIBUTED_GA_COUNT = 4
private const val DISTRIBUTED_MAX_ITERATION = 5
private const val POPULATION_SIZE = 50
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42
private const val ELITISM = 5
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01
private const val MAX_ITERATION = 20

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
private fun main() {
    val dga = dGA(
        factory = { booleans(size = CHROMOSOME_SIZE) },
        fitnessFunction = { value -> value.count { it } },
    ) {
        random = Random(seed = RANDOM_SEED)

        parallelismConfig {
            // distributed parallelism - run each child GA independently in their own coroutine!
            workersCount = DISTRIBUTED_GA_COUNT
        }

        +pGAs(
            count = DISTRIBUTED_GA_COUNT,
            population = { population(size = POPULATION_SIZE) },
        ) {
            elitism = ELITISM

            evolve {
                selTournament(size = TOURNAMENT_SIZE) // selection ('sel' prefix)
                cxOnePoint(chance = CROSSOVER_CHANCE) // crossover ('cx' prefix)
                mutFlipBit(chance = MUTATION_CHANCE, flipBitChance = FLIP_BIT_CHANCE) // mutation ('mut' prefix)
                evaluation()
                stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE } // stop condition
            }
        }

        evolve(
            useDefault = true, // (default is true) uses launchChildren (see) operator before evolution function below
        ) {
            println("Iteration $iteration: best fitness = $bestFitness")
            children.forEach { child -> println("${child.name}: best fitness = ${child.bestFitness}") }
            // limit only iterations for dGA not need stop condition by here cause useDefault is true
            stopBy(maxIteration = DISTRIBUTED_MAX_ITERATION)
            migration(percent = 0.1)
        }
    }
    dga.startBlocking() // start on Main Thread

    println("Result fitness: ${dga.bestFitness}")
    println("Result chromosome: ${dga.best?.value?.joinToString()}")
}
