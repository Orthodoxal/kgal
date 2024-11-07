package kgal.cellular.operators

import kgal.cellular.*
import kgal.chromosome.Chromosome
import kgal.dsl.ConfigDslMarker
import kgal.processor.process
import kgal.size
import kotlin.random.Random

/**
 * Unique genetic operator for [CellularGA] performs an evolutionary strategy [evolution] for cells in [CellularPopulation].
 * The execution process is carried out depending on the [CellularType].
 *
 * Example:
 * ```
 * evolve {
 *     evolveCells {
 *         selTournament(size = 3)
 *         cxOnePoint(chance = 0.9)
 *         mutFlipBit(chance = 0.2, flipBitChance = 0.02)
 *         evaluation()
 *     }
 *
 *     println("Iteration $iteration: best fitness = $bestFitness")
 *     stopBy(maxIteration = MAX_ITERATION) { bestFitness == CHROMOSOME_SIZE }
 * }
 * ```
 *
 * Fully supports elitism - a child occupies a parent's cell if its fitness function value is greater.
 * @param evolution evolutionary strategy for individual cell using [CellLifecycle]
 * @see replaceWithElitism
 * @see synchronousExecute
 * @see asynchronousExecute
 * @see CellularType
 */
public suspend inline fun <V, F> CellularLifecycle<V, F>.evolveCells(
    crossinline evolution: (@ConfigDslMarker CellLifecycle<V, F>).() -> Unit,
) {
    when (val cellularType = cellularType) {
        is CellularType.Synchronous -> {
            synchronousExecute { chromosome, neighbors, random ->
                with(CellLifecycle(chromosome, neighbors, random)) { evolution(); cell }
            }
        }

        is CellularType.Asynchronous -> {
            asynchronousExecute(cellularType.updatePolicy) { chromosome, neighbors, random ->
                with(CellLifecycle(chromosome, neighbors, random)) { evolution(); cell }
            }
        }
    }
}

/**
 * Executes [CellularType.Synchronous] mode (creating temp target population) for [CellularGA].
 */
public suspend inline fun <V, F> CellularLifecycle<V, F>.synchronousExecute(
    crossinline cellEvolution: (chromosome: Chromosome<V, F>, neighbors: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
) {
    val tempPopulation = population.copyOf()
    process { iteration, random ->
        executeCellEvolution(random, iteration, tempPopulation, cellEvolution)
    }
    population.set(tempPopulation)
}

/**
 * Executes [CellularType.Asynchronous] mode for [CellularGA].
 * @see UpdatePolicy
 */
public suspend inline fun <V, F> CellularLifecycle<V, F>.asynchronousExecute(
    updatePolicy: UpdatePolicy,
    crossinline cellEvolution: (chromosome: Chromosome<V, F>, neighbors: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
): Unit = when (updatePolicy) {
    is UpdatePolicy.LineSweep -> {
        process { iteration, random ->
            executeCellEvolution(random, iteration, population.get(), cellEvolution)
        }
    }

    is UpdatePolicy.FixedRandomSweep -> {
        val indicesShuffled = updatePolicy.cacheIndices(size)
        process { iteration, random ->
            val index = indicesShuffled[iteration]
            executeCellEvolution(random, index, population.get(), cellEvolution)
        }
    }

    is UpdatePolicy.NewRandomSweep -> {
        val indicesShuffled = IntArray(size) { it }.apply { shuffle(random) }
        process { iteration, random ->
            val index = indicesShuffled[iteration]
            executeCellEvolution(random, index, population.get(), cellEvolution)
        }
    }

    is UpdatePolicy.UniformChoice -> {
        process { _, random ->
            val index = random.nextInt(size)
            executeCellEvolution(random, index, population.get(), cellEvolution)
        }
    }
}

/**
 * Executes [action] with [process] for [CellularGA].
 * @see process
 */
public suspend inline fun <V, F> CellularLifecycle<V, F>.process(
    crossinline action: suspend (iteration: Int, random: Random) -> Unit,
) {
    process(
        parallelismLimit = parallelismConfig.workersCount,
        startIteration = 0,
        endIteration = size,
        action = action,
    )
}

/**
 * Executes [cellEvolution] for cell in [CellularLifecycle.population] with [index] and set result to [target].
 * @param random random for safe [cellEvolution] execution
 * @param target target population where the result of [cellEvolution] is placed
 * @see replaceWithElitism
 */
public inline fun <V, F> CellularLifecycle<V, F>.executeCellEvolution(
    random: Random,
    index: Int,
    target: Array<Chromosome<V, F>>,
    crossinline cellEvolution: (chromosome: Chromosome<V, F>, neighbors: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
) {
    val chromosome = population[index]
    val chromosomeneighborsIndices = neighborsIndicesCache[index]
    val chromosomeneighbors = Array(chromosomeneighborsIndices.size) { indexneighbor ->
        population[chromosomeneighborsIndices[indexneighbor]]
    }
    val result = cellEvolution(chromosome.clone(), chromosomeneighbors, random)
    replaceWithElitism(target, index, chromosome, result)
}

/**
 * Executes replacing [old] chromosome with [new] when:
 * - [CellularLifecycle.elitism] = `false`
 * - [CellularLifecycle.elitism] = `true` AND `new.fitness > old.fitness`
 * @param population target population
 * @param index target index of chromosome in population
 */
public fun <V, F> CellularLifecycle<V, F>.replaceWithElitism(
    population: Array<Chromosome<V, F>>,
    index: Int,
    old: Chromosome<V, F>,
    new: Chromosome<V, F>,
) {
    if (elitism) {
        if (new > old) population[index] = new
    } else {
        population[index] = new
    }
}
