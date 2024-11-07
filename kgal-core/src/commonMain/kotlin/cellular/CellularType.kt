package kgal.cellular

import kgal.cellular.CellularType.Asynchronous
import kgal.cellular.CellularType.Synchronous
import kotlin.random.Random

/**
 * [CellularGA] supports SS supports two modes of operation, called types:
 * - [Synchronous] default
 * - [Asynchronous]
 */
public sealed interface CellularType {

    /**
     * In a regular synchronous [CellularGA], the algorithm proceeds from the first chromosome to the last one
     * by using the information from parent generation population to create a new temporary `offspring` population.
     * After finishing the old parent generation population is completely and synchronously replaced with the new temporary `offspring` population.
     * Usually, the replacement keeps the best chromosomes in the same position of both populations, that is, `elitism` is used.
     */
    public data object Synchronous : CellularType

    /**
     * In an asynchronous [CellularGA] the `offspring` resulting from evolution, instead of being placed in a temporary `offspring` population (as [Synchronous] does),
     * immediately replaces its parent in the current generation population and can immediately take part in the evolution of its neighbors in place of its parent.
     * The order of evolution of a population's chromosomes depends on [updatePolicy].
     * Thus, the asynchronous type does not waste resources on creating a temporary `offspring` population,
     * however, none of [updatePolicy] guarantees the same result when running in parallel mode.
     * @see updatePolicy
     */
    public data class Asynchronous(val updatePolicy: UpdatePolicy = UpdatePolicy.LineSweep) : CellularType
}

/**
 * [UpdatePolicy] - renewal policy determining the order of chromosome's evolution in [CellularGA] with [CellularType.Asynchronous] type.
 */
public sealed interface UpdatePolicy {

    /**
     * Basic [UpdatePolicy] defining the order of evolution from the first to the last chromosome in a [CellularPopulation],
     * like [CellularType.Synchronous].
     * - all chromosome in population will be evolved only once
     */
    public data object LineSweep : UpdatePolicy

    /**
     * [UpdatePolicy] defining the order of evolution with new random sweep: instead of passing from the first to the last chromosome,
     * an array chromosomes' indices is created and randomly shuffled, then a pass is made through the elements of this array.
     * The chromosome whose index is indicated in the current cell of the array evolves.
     * - all chromosome in population will be evolved only once
     */
    public data object NewRandomSweep : UpdatePolicy

    /**
     * [UpdatePolicy] defining the order of evolution with uniform choice: chromosome for evolution is chosen randomly.
     * - it is not guaranteed that all chromosome in population will be evolved
     * - it is not guaranteed that chromosome in population will be evolved only once
     * (Several evolutions are possible for one chromosome)
     */
    public data object UniformChoice : UpdatePolicy

    /**
     * [UpdatePolicy] defining the order of evolution with fixed random sweep:
     * similar to [NewRandomSweep] but an array chromosomes' indices is created and shuffled with [random], then it will be cached.
     * - an array chromosomes' indices created only once for current size (if size changes array will be re-cached), see [cacheIndices].
     * - all chromosome in population will be evolved only once
     */
    public data class FixedRandomSweep(val random: Random) : UpdatePolicy {

        /**
         * A cached array of indices
         */
        private lateinit var indicesShuffled: IntArray

        /**
         * Get cached array of indices.
         * If [size] != [indicesShuffled].size cached array will be recreated.
         */
        public fun cacheIndices(size: Int): IntArray {
            if (!this::indicesShuffled.isInitialized || size != this.indicesShuffled.size) {
                indicesShuffled = IntArray(size) { it }.apply { shuffle(random) }
            }
            return indicesShuffled
        }
    }
}
