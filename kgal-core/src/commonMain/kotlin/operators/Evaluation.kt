package kgal.operators

import kgal.Lifecycle
import kgal.Population
import kgal.chromosome.Chromosome
import kgal.processor.process

/**
 * Base evaluates function for [Chromosome] by [fitnessFunction]
 */
public inline fun <V, F> Chromosome<V, F>.evaluate(fitnessFunction: (V) -> F) {
    fitness = fitnessFunction(value)
}

/**
 * Base evaluates function for all chromosomes in [Population] by [fitnessFunction]
 * @param start first index of chromosome (inclusive)
 * @param end last index of chromosome (exclusive)
 * @param parallelismLimit limit of parallel workers
 */
public suspend inline fun <V, F> Lifecycle<V, F>.evaluateAll(
    start: Int,
    end: Int,
    parallelismLimit: Int,
    crossinline fitnessFunction: (V) -> F,
) {
    process(
        parallelismLimit = parallelismLimit,
        startIteration = start,
        endIteration = end,
        action = { index, _ -> population[index].evaluate(fitnessFunction) },
    )
}
