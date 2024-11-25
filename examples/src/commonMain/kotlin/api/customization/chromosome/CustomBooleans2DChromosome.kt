package api.customization.chromosome

import kgal.chromosome.Chromosome

/**
 * [CustomBooleans2DChromosome] - custom chromosome based on [BooleanArray]s.
 *
 * ```
 * [
 * [true, false, true, ... , true],
 * [true, true, false, ... , true],
 * [false, false, true, ... , false],
 * ...
 * [true, false, true, ... , false],
 * ]
 * ```
 *
 * Example for Creating [CustomBooleans2DChromosome]:
 */
internal data class CustomBooleans2DChromosome(
    // STEP 1: override value with (Array<BooleanArray>) and fitness value (For example Int)
    override var value: Array<BooleanArray>,
    override var fitness: Int? = null, // default value is null (not evaluated)
) : Chromosome<Array<BooleanArray>, Int> { // Make the class the successor of class Chromosome

    // STEP 2: override how compare these chromosomes for order (Cause fitness value is Int, compare only fitness values)
    override fun compareTo(other: Chromosome<Array<BooleanArray>, Int>): Int = compareValues(fitness, other.fitness)

    // STEP 3: override equals for this chromosome
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CustomBooleans2DChromosome

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentDeepEquals(other.value)
    }

    // STEP 4: override hashcode
    override fun hashCode(): Int {
        var result = value.contentDeepHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    // STEP 5: override how clone this chromosome
    override fun clone(): Chromosome<Array<BooleanArray>, Int> =
        copy(
            value = Array(value.size) { value[it].copyOf() },
        )
}
