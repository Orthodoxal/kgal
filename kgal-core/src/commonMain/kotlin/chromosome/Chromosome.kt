package kgal.chromosome

/**
 * [Chromosome] interface represents Individual - one of many solutions to a specific problem, where:
 *
 * @property [value] is the structure of the solution defined by generic
 * @property [fitness] is the effectiveness of this solution defined by generic
 *
 * @see [kgal.chromosome.base.ChromosomeArray]
 * @see [kgal.chromosome.base.ChromosomeBooleanArray]
 * @see [kgal.chromosome.base.ChromosomeByteArray]
 * @see [kgal.chromosome.base.ChromosomeCharArray]
 * @see [kgal.chromosome.base.ChromosomeDoubleArray]
 * @see [kgal.chromosome.base.ChromosomeFloatArray]
 * @see [kgal.chromosome.base.ChromosomeIntArray]
 * @see [kgal.chromosome.base.ChromosomeLongArray]
 * @see [kgal.chromosome.base.ChromosomeMutableList]
 * @see [kgal.chromosome.base.ChromosomeShortArray]
 * @see [kgal.chromosome.base.ChromosomeString]
 */
public interface Chromosome<V, F> : Comparable<Chromosome<V, F>> {
    /**
     * Combination of genes for a [Chromosome] (individual) (Often an array or string)
     */
    public var value: V

    /**
     * Calculated value by fitness function for a specific individual
     */
    public var fitness: F?

    /**
     * Clone function to create deap copied [Chromosome] with the same [value] and [fitness]
     */
    public fun clone(): Chromosome<V, F>
}
