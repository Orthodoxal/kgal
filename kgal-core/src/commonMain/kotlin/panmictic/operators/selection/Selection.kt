package kgal.panmictic.operators.selection

import kgal.chromosome.Chromosome
import kgal.panmictic.PanmicticGA
import kgal.panmictic.PanmicticLifecycle
import kgal.processor.process
import kgal.size
import kotlin.random.Random

/**
 * Performs a selection step for population in [PanmicticGA].
 *
 * Welcome to use for your own implementations! It is a base function for executing [selection] step in [PanmicticGA].
 * Fully supports elitism - elite chromosomes go through the selection stage by default.
 * @param parallelismLimit limit of parallel workers
 * @param selection specific selection action (How chromosomes will be selected from current population)
 * @see [PanmicticLifecycle.elitism]
 */
public suspend inline fun <V, F> PanmicticLifecycle<V, F>.selection(
    parallelismLimit: Int,
    crossinline selection: (source: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
) {
    if (elitism > 0) {
        recalculateEliteChromosomes()
    } else if (elitism < 0) {
        throw IllegalStateException("Elitism must be more or equal to zero")
    }

    val tempPopulation = population.copyOf()
    process(
        parallelismLimit = parallelismLimit,
        startIteration = elitism,
        endIteration = size,
        action = { index, random ->
            tempPopulation[index] = selection(population.get(), random)
        },
    )
    population.set(tempPopulation)
}

/**
 * Performs a selection step for population in [PanmicticGA].
 * Suitable for selection mechanisms where behavior depends on indices. For example: [selTournament]
 *
 * Welcome to use for your own implementations! It is a base function for executing [selection] step in [PanmicticGA].
 * Fully supports elitism - elite chromosomes go through the selection stage by default.
 * @param parallelismLimit limit of parallel workers
 * @param selection specific selection action with index (How chromosomes will be selected from current population)
 * @see [PanmicticLifecycle.elitism]
 */
public suspend inline fun <V, F> PanmicticLifecycle<V, F>.selectionWithIndex(
    parallelismLimit: Int,
    crossinline selection: (index: Int, source: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
) {
    if (elitism > 0) {
        recalculateEliteChromosomes()
    } else if (elitism < 0) {
        throw IllegalStateException("Elitism must be more or equal to zero")
    }

    val tempPopulation = population.copyOf()
    process(
        parallelismLimit = parallelismLimit,
        startIteration = elitism,
        endIteration = size,
        action = { index, random ->
            tempPopulation[index] = selection(index, population.get(), random)
        },
    )
    population.set(tempPopulation)
}
