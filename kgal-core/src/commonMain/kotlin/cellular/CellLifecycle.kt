package kgal.cellular

import kgal.chromosome.Chromosome
import kgal.processor.process
import kgal.size
import kotlin.random.Random

public interface CellLifecycle<V, F> : CellularLifecycle<V, F> {
    public var cellChromosome: Chromosome<V, F>
    public val neighbours: Array<Chromosome<V, F>>
}

internal class CellLifecycleInstance<V, F>(
    override var cellChromosome: Chromosome<V, F>,
    override val neighbours: Array<Chromosome<V, F>>,
    override val random: Random,
    cellularLifecycle: CellularLifecycle<V, F>,
) : CellLifecycle<V, F>, CellularLifecycle<V, F> by cellularLifecycle

public fun <V, F> CellularLifecycle<V, F>.cellLifecycle(
    cellChromosome: Chromosome<V, F>,
    neighbours: Array<Chromosome<V, F>>,
    random: Random,
): CellLifecycle<V, F> = CellLifecycleInstance(cellChromosome, neighbours, random, this)

public fun <V, F> buildCellularLifecycle(
    parallelismLimit: Int,
    beforeLifecycleIteration: (suspend CellularLifecycle<V, F>.() -> Unit)? = null,
    afterLifecycleIteration: (suspend CellularLifecycle<V, F>.() -> Unit)? = null,
    cellLifecycle: suspend CellularLifecycle<V, F>.(chromosome: Chromosome<V, F>, neighbours: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
): suspend CellularLifecycle<V, F>.() -> Unit = {
    beforeLifecycleIteration?.invoke(this)
    when (val cellularType = cellularType) {
        is CellularType.Synchronous -> {
            synchronousExecute(parallelismLimit, cellLifecycle)
        }

        is CellularType.Asynchronous -> {
            asynchronousExecute(cellularType.updatePolicy, parallelismLimit, cellLifecycle)
        }
    }
    afterLifecycleIteration?.invoke(this)
}

private suspend inline fun <V, F> CellularLifecycle<V, F>.synchronousExecute(
    parallelismLimit: Int,
    crossinline cellLifecycle: suspend CellularLifecycle<V, F>.(chromosome: Chromosome<V, F>, neighbours: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
) {
    val tempPopulation = population.copyOf()
    process(parallelismLimit) { iteration, random ->
        processCellLifecycle(random, iteration, tempPopulation, cellLifecycle)
    }
    population.set(tempPopulation)
}

private suspend inline fun <V, F> CellularLifecycle<V, F>.asynchronousExecute(
    updatePolicy: UpdatePolicy,
    parallelismLimit: Int,
    crossinline cellLifecycle: suspend CellularLifecycle<V, F>.(chromosome: Chromosome<V, F>, neighbours: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
) = when (updatePolicy) {
    is UpdatePolicy.LineSweep -> {
        process(parallelismLimit) { iteration, random ->
            processCellLifecycle(random, iteration, population.get(), cellLifecycle)
        }
    }

    is UpdatePolicy.FixedRandomSweep -> {
        val indicesShuffled = updatePolicy.cacheIndices(size)
        process(parallelismLimit) { iteration, random ->
            val index = indicesShuffled[iteration]
            processCellLifecycle(random, index, population.get(), cellLifecycle)
        }
    }

    is UpdatePolicy.NewRandomSweep -> {
        val indicesShuffled = IntArray(size) { it }.apply { shuffle(random) }
        process(parallelismLimit) { iteration, random ->
            val index = indicesShuffled[iteration]
            processCellLifecycle(random, index, population.get(), cellLifecycle)
        }
    }

    is UpdatePolicy.UniformChoice -> {
        process(parallelismLimit) { _, random ->
            val index = random.nextInt(size)
            processCellLifecycle(random, index, population.get(), cellLifecycle)
        }
    }
}

private suspend inline fun <V, F> CellularLifecycle<V, F>.process(
    parallelismLimit: Int,
    crossinline action: suspend (iteration: Int, random: Random) -> Unit,
) {
    process(
        parallelismLimit = parallelismLimit,
        startIteration = 0,
        endIteration = size,
        action = action,
    )
}

private suspend inline fun <V, F> CellularLifecycle<V, F>.processCellLifecycle(
    random: Random,
    index: Int,
    target: Array<Chromosome<V, F>>,
    crossinline cellLifecycle: suspend CellularLifecycle<V, F>.(chromosome: Chromosome<V, F>, neighbours: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
) {
    val chromosome = population[index]
    val chromosomeNeighboursIndices = neighboursIndicesCache[index]
    val chromosomeNeighbours = Array(chromosomeNeighboursIndices.size) { indexNeighbour ->
        population[chromosomeNeighboursIndices[indexNeighbour]]
    }
    val result = cellLifecycle(chromosome.clone(), chromosomeNeighbours, random)
    replaceWithElitism(elitism, target, index, chromosome, result)
}

private fun <V, F> replaceWithElitism(
    elitism: Boolean,
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
