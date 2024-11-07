package kgal.cellular

import kgal.AbstractLifecycle
import kgal.Lifecycle
import kgal.cellular.operators.replaceWithElitism
import kgal.chromosome.Chromosome

/**
 * [CellularLifecycle] - specific [Lifecycle] for [CellularGA].
 *
 * [V] - value of [Chromosome]
 *
 * [F] - fitness value of [Chromosome]
 *
 * Creates with CellularLifecycle().
 * @see Lifecycle
 */
public interface CellularLifecycle<V, F> : Lifecycle<V, F> {

    /**
     * Flag for elitism in [CellularGA].
     *
     * If `true` parent chromosome will be replaced with child only if `child.fitness > parent.fitness`.
     * @see replaceWithElitism
     */
    public var elitism: Boolean

    /**
     * Cellular type for [CellularGA].
     * @see CellularType
     */
    public var cellularType: CellularType

    /**
     * Neighborhood for [CellularGA].
     * @see [CellularNeighborhood]
     */
    public var neighborhood: CellularNeighborhood

    /**
     * Actual cache for neighbor's indices (in a projection one-dimensional array) in [CellularGA] for each cell.
     * Calculating neighbors indices is expensive for each iteration; cached values are stored in this property.
     *
     * Example cache structure:
     * ```
     * 0: 2, 6, 3, 1 // neighbor's indices for target cell 0
     * 1: 0, 7, 4, 2 // neighbor's indices for target cell 1
     * 2: 1, 8, 5, 0 // neighbor's indices for target cell 2
     * 3: 5, 0, 6, 4 // neighbor's indices for target cell 3
     * ... etc.
     * ```
     */
    public var neighborsIndicesCache: Array<IntArray>

    /**
     * Override base population as [CellularPopulation] for [CellularGA].
     */
    public override val population: CellularPopulation<V, F>
}

/**
 * Creates an instance of [CellularLifecycle] by [cellularGA] and [cellularConfig].
 */
public fun <V, F> CellularLifecycle(
    cellularGA: CellularGA<V, F>,
    cellularConfig: CellularConfig<V, F>,
): CellularLifecycle<V, F> =
    CellularLifecycleInstance(cellularGA, cellularConfig)

/**
 * Base realization of [CellularLifecycle].
 * @see AbstractLifecycle
 */
internal class CellularLifecycleInstance<V, F>(
    private val cellularGA: CellularGA<V, F>,
    cellularConfig: CellularConfig<V, F>,
) : CellularLifecycle<V, F>, AbstractLifecycle<V, F>(cellularGA, cellularConfig) {

    override val population: CellularPopulation<V, F>
        get() = cellularGA.population

    override var elitism: Boolean
        get() = cellularGA.elitism
        set(value) {
            cellularGA.elitism = value
        }

    override var cellularType: CellularType
        get() = cellularGA.cellularType
        set(value) {
            cellularGA.cellularType = value
        }

    override var neighborhood: CellularNeighborhood
        get() = cellularGA.neighborhood
        set(value) {
            cellularGA.neighborhood = value
        }

    override var neighborsIndicesCache: Array<IntArray> = emptyArray()
}
