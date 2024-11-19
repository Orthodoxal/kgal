package kgal.cellular

import kgal.EvolveScope
import kgal.chromosome.Chromosome
import kotlin.random.Random

/**
 * [CellEvolveScope] - evolveScope for `cellular evolutionary strategy` based on the evolution of a [cell] dependent on its [neighbors].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 * ```
 * // Structure of a cellular population example:
 * Dimens.square(length = 5)
 * VonNeumann(radius = 1)
 *
 * X   X   X   X   X
 * X   X   N   X   X
 * X   N   C   N   X
 * X   X   N   X   X
 * X   X   X   X   X
 * ```
 * Where `C` - cell chromosome, `N` - neighbors for current cell chromosomes, `X` - other chromosomes in population.
 *
 * The `cellular evolutionary strategy` is that all genetic operators are applied only on the target [cell] using only its [neighbors]:
 * `X` chromosomes have no direct influence on the `C` chromosome during `cellular evolution`.
 * Gene transfer between chromosomes that are not neighbors of each other occurs through common neighbors or common neighbors of neighbors, etc.
 *
 * Not a standalone implementation of [EvolveScope], it exists exclusively in the context of [CellularEvolveScope].
 *
 * Creates with CellEvolveScope().
 * @see CellularEvolveScope
 */
public interface CellEvolveScope<V, F> {

    /**
     * Random for single `cellular evolution`. Defines a pseudorandom number generator for predictive calculations.
     */
    public val random: Random

    /**
     * The target cell-chromosome for which evolution will occur.
     */
    public var cell: Chromosome<V, F>

    /**
     * The neighbors chromosomes of the target [cell] chromosome used by genetic operators as participants in evolution for [cell].
     */
    public val neighbors: Array<Chromosome<V, F>>

    /**
     * Fitness function - a function that evaluates the quality or "fitness" of each individual (chromosome) in a population.
     * The fitness function determines how well a particular solution matches the target problem.
     * It can be changed.
     */
    public var fitnessFunction: (V) -> F
}

/**
 * Creates an instance of [CellEvolveScope].
 * @see CellEvolveScope
 */
public fun <V, F> CellularEvolveScope<V, F>.CellEvolveScope(
    cellChromosome: Chromosome<V, F>,
    neighbors: Array<Chromosome<V, F>>,
    random: Random,
): CellEvolveScope<V, F> = CellEvolveScopeInstance(random, cellChromosome, neighbors, fitnessFunction)

/**
 * Base realization of [CellEvolveScope].
 */
internal class CellEvolveScopeInstance<V, F>(
    override val random: Random,
    override var cell: Chromosome<V, F>,
    override val neighbors: Array<Chromosome<V, F>>,
    override var fitnessFunction: (V) -> F,
) : CellEvolveScope<V, F>
