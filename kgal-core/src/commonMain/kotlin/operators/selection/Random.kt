package kgal.operators.selection

import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * Select randomly chromosome from [source]
 * @return [Chromosome]
 */
public fun <V, F> selectionRandom(source: Array<Chromosome<V, F>>, random: Random): Chromosome<V, F> =
    source.random(random)
