package panmictic

import kgal.*
import kgal.chromosome.Chromosome
import kgal.chromosome.base.ChromosomeBooleanArray
import kgal.operators.isSteadyGenerations
import kgal.operators.shakeBy
import kgal.operators.stopBy
import kgal.panmictic.operators.adjustSize
import kgal.panmictic.operators.crossover.CrossoverType
import kgal.panmictic.operators.crossover.cxOnePoint
import kgal.panmictic.operators.evaluation
import kgal.panmictic.operators.mutation.mutFlipBit
import kgal.panmictic.operators.selection.selTournament
import kgal.panmictic.pGA
import kgal.panmictic.population
import kgal.panmictic.restart
import kgal.processor.parallelism.ParallelismConfig
import kgal.statistics.allSessions
import kgal.statistics.note.Statistic
import kgal.statistics.note.stat
import kgal.statistics.stats.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

// Constants (can be changed)
private const val POPULATION_SIZE_INIT = 200
private const val MAX_ITERATION = 50
private const val POPULATION_BUFFER = MAX_ITERATION
private const val CHROMOSOME_SIZE = 100
private const val RANDOM_SEED = 42
private const val ELITISM = 10
private const val TOURNAMENT_SIZE = 3
private const val CROSSOVER_CHANCE = 0.8
private const val MUTATION_CHANCE = 0.2
private const val FLIP_BIT_CHANCE = 0.01

/**
 * Represents the main capabilities of the kgal api based on OneMax task (see in examples).
 */
private fun main() { // Run it!
    // STEP 1:
    /**
     * Create custom Chromosome Instance with value [BooleanArray] and fitness [Int] or use base [ChromosomeBooleanArray].
     *
     * Example for Creating Custom Chromosome:
     */
    data class CustomBooleansChromosome(
        override var value: BooleanArray,
        override var fitness: Int? = null, // default value is null
    ) : Chromosome<BooleanArray, Int> {

        // Override how compare your chromosomes
        override fun compareTo(other: Chromosome<BooleanArray, Int>): Int = compareValues(fitness, other.fitness)

        // Override how compare your chromosomes
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as CustomBooleansChromosome

            if (fitness != null && other.fitness != null && fitness != other.fitness) return false
            return value.contentEquals(other.value)
        }

        override fun hashCode(): Int {
            var result = value.contentHashCode()
            result = 31 * result + (fitness?.hashCode() ?: 0)
            return result
        }

        // Override how clone your chromosomes
        override fun clone(): Chromosome<BooleanArray, Int> = copy(value = value.copyOf())
    }

    // STEP 2:
    // Generate custom population (Or get it from any source)
    val randomGen = Random(seed = RANDOM_SEED)
    // Create factory function for chromosome
    val factory: PopulationFactory<BooleanArray, Int> = {
        CustomBooleansChromosome(value = BooleanArray(CHROMOSOME_SIZE) { nextBoolean() })
    }
    // Generate initial population and prepare panmictic population source (array) with maximum size: (size + buffer)
    val populationGen = Array(POPULATION_SIZE_INIT + POPULATION_BUFFER) { randomGen.factory() }

    // STEP 3:
    // Create Panmictic (Classical) Genetic Algorithm for OneMax task with pGA()
    val pga = pGA(
        // Set population configuration (Custom)
        population = population(
            population = populationGen,
            buffer = POPULATION_BUFFER,
            name = "MY_POPULATION",
            factory = factory,
        ),
        // Set fitness function
        fitnessFunction = { value -> value.count { it } },
    ) {
        // STEP 4:
        // Configure Genetic Algorithm
        random = Random(seed = RANDOM_SEED) // set pseudo random number generator (repeatable results)
        elitism = ELITISM // set elitism

        // Set up parallelism
        parallelismConfig {
            workersCount = 5 // Count coroutines in parallel mode
            dispatcher = Dispatchers.Default // Set up dispatcher for parallelism
        }

        // Set up statistics
        statisticsConfig {
            coroutineContext = Dispatchers.IO // Set up dispatcher for statistics
            enableDefaultCollector = false // Turn of default collector for statistics
            guaranteedSorted = true // Prepare statistics to sorted population in descending order
            // See other params for statistics flow
        }

        // Set up action on start evolution
        before {
            println("GA STARTED, Init population fitness values:\n${population.joinToString { it.fitness.toString() }}")
        }

        // Set up evolution strategy as function
        evolve {
            // Imitate hardworking
            delay(timeMillis = 200)

            // Selection stage (prefix = "sel")
            selTournament(
                size = TOURNAMENT_SIZE,
                parallelismLimit = ParallelismConfig.NO_PARALLELISM // disable parallel operation
            )

            /* Create custom selection operator example with selection help-function
            selection(
                parallelismLimit = 3,
            ) { source: Array<Chromosome<BooleanArray, Int>>, random: Random ->
                source[random.nextInt(size)]
            }
            */

            // Crossover stage (prefix = "cx")
            cxOnePoint(
                chance = CROSSOVER_CHANCE,
                crossoverType = CrossoverType.Randomly, // Set up as randomly crossover
            )

            /* Create custom crossover operator example with crossover help-function
            crossover(
                chance = CROSSOVER_CHANCE,
                parallelismLimit = parallelismConfig.workersCount,
                crossoverType = CrossoverType.Iterative,
            ) { chromosome1, chromosome2, random ->
                // Using base crossoverUniform function
                crossoverUniform(chromosome1.value, chromosome2.value, chance = 0.01, random)
            }
            */

            // Mutation stage (prefix = "mut")
            mutFlipBit(
                chance = MUTATION_CHANCE,
                flipBitChance = FLIP_BIT_CHANCE,
            )

            /* Create custom mutation operator example (equal to mutFlipBit above) with mutation help-function
            mutation(
                chance = MUTATION_CHANCE,
                parallelismLimit = ParallelismConfig.NO_PARALLELISM,
            ) { chromosome, random ->
                chromosome.value.forEachIndexed { index, gene ->
                    randomByChance(FLIP_BIT_CHANCE, random) { chromosome.value[index] = !gene }
                }
            }
            */

            /**
             * Create your own genetic operator here. Use [Lifecycle.store] for saving any values through iterations.
             * @see Lifecycle.store
             */
            /*
            val previousValue = store["MY_VALUE"]
             for (chromosome in population) {

                // do anything here
             }
             */

            // Resize stage
            adjustSize(
                step = 1, // Up current size by 1 (takes from buffer)
                evaluateBuffered = false, // Optimize to false cause next stage is evaluation
            )

            // Evaluation stage
            evaluation(
                sortAfter = true, // Cause guaranteedSorted is true and next step is statistics
            )

            println("GA Iteration: $iteration on coroutine: $coroutineContext")
            // Send statistics stage
            stat(
                timeIteration(),
                bestFitness(),
                mean(),
                median(),
                worstFitness(),
                Statistic(name = "BUFFER_SIZE", value = population.buffer) // Send custom statistics
            )

            // Shake on steady stage (Combination of 2 genetic operators)
            // It will shake 20% of population from the end (drop old and create new chromosomes) on steady generation.
            // Steady generation - generation with the same specific param (bestFitness by default)
            shakeBy(percent = 0.2) { isSteadyGenerations(targetIterationCount = 5) }

            /* // This absolute equivalent to steady stage for more control
            onSteadyGenerations(targetIterationCount = 5) {
                val (from, to) = shake(percent = 0.2)
                evaluateAll(from, to, parallelismConfig.workersCount, fitnessFunction)
            }*/

            // Stop condition stage
            stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE } // finish GA by conditions
        }

        // Set up action on finish evolution
        after {
            println("GA FINISHED, Result = $bestFitness on iteration: $iteration")
        }
    }

    // STEP 5: create collectors for statistics
    // Collect statistics values and print them to console
    pga.collect(id = "Standard collector") { value ->
        println("Standard collector:")
        println(value)
        // This collector would be active only when:
        if (value.iteration == MAX_ITERATION / 2) {
            throw CancellationException(
                "Cancelling statistics collector by CancellationException. " +
                        "It is safe cause it does not stop GA and other collectors"
            )
        }
    }

    // Collect statistics values with another advanced collector by statisticsProvider
    pga.statisticsProvider
        .collect(id = "Advanced collector: ") { statisticsFlow ->
            // Hot flow of statistics values
            statisticsFlow
                .onStart { println("Starting to collect statistic values by advanced collector") }
                .onEach {
                    // Imitate hardworking for collector
                    delay(timeMillis = 250)
                }
                .collect { value ->
                    println("Advanced collector:")
                    println("${value.statistic.name} collected for ${value.iteration} iteration!\n")
                }
        }


    // STEP 6: Testing GA Api
    runBlocking { // starting with coroutines
        // Start GA
        println("Started")
        launch(Dispatchers.Default) { pga.start() }

        // Wait a little bit
        delay(timeMillis = 1_000)

        // Stop GA
        println("Stopped by default")
        pga.stop(stopPolicy = StopPolicy.Default)
        println("Waiting for stop done. All collectors handled statistics")

        // Waiting
        delay(timeMillis = 2_000)

        // Resume GA
        println("Resumed")
        launch(Dispatchers.Default) { pga.resume() }

        // Waiting
        delay(timeMillis = 5_000)

        // Restart GA
        println("Restarted, stats values not actual, statistics restart without wait handling")
        launch(Dispatchers.Default) { pga.restart() }

        // Waiting
        delay(timeMillis = 3_000)

        // Stop GA with Timeout policy
        println("Stopped by timeout")
        pga.stop(stopPolicy = StopPolicy.Timeout(millis = 100))

        // Restart GA again (On Main Thread)
        println("Restarted on main thread")
        println(coroutineContext.job)
        // Restart with resized panmictic population to initial value.
        pga.restart(populationSize = POPULATION_SIZE_INIT, populationBuffer = POPULATION_BUFFER)
        println("Best fitness result for population ${pga.population.name}: ${pga.bestFitness}")
    }

    // STEP 7: Explore GA sessions
    pga.allSessions.print()
    // Total duration: STARTED -> LAST TIME VALUE
    println("Time total: ${pga.timeTotal} // includes collectors waiting")
    // Total activity duration:
    // STARTED -> FINISHED + STARTED (by restart) -> STOPPED + STARTED -> FINISHED
    println("Activity time total: ${pga.activeTimeTotal} // without stops, includes collectors waiting")
}
