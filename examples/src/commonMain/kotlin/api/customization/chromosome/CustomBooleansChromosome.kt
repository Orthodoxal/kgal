package api.customization.chromosome

import kgal.chromosome.Chromosome
import kgal.chromosome.base.ChromosomeBooleanArray

/**
 * [CustomBooleansChromosome] - custom chromosome based on [BooleanArray] (Analog of the base [ChromosomeBooleanArray]).
 *
 * `[true, false, true, ... , false]`
 *
 * Example for Creating [CustomBooleansChromosome]:
 */
internal data class CustomBooleansChromosome(
    // STEP 1: override value with (BooleanArray) and fitness value (For example Int)
    override var value: BooleanArray,
    override var fitness: Int? = null, // default value is null (not evaluated)
) : Chromosome<BooleanArray, Int> { // Make the class the successor of class Chromosome

    // STEP 2: override how compare these chromosomes for order (Cause fitness value is Int, compare only fitness values)
    override fun compareTo(other: Chromosome<BooleanArray, Int>): Int = compareValues(fitness, other.fitness)

    // STEP 3: override equals for this chromosome
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CustomBooleansChromosome

        if (fitness != null && other.fitness != null && fitness != other.fitness) return false
        return value.contentEquals(other.value)
    }

    // STEP 4: override hashcode
    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + (fitness?.hashCode() ?: 0)
        return result
    }

    // STEP 5: override how clone this chromosome
    override fun clone(): Chromosome<BooleanArray, Int> = copy(value = value.copyOf())
}
