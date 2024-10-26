package kgal.cellular

import kgal.AbstractLifecycle
import kgal.Lifecycle

public interface CellularLifecycle<V, F> : Lifecycle<V, F> {

    public var elitism: Boolean

    public var cellularType: CellularType

    public var neighborhood: CellularNeighborhood

    public var neighboursIndicesCache: Array<IntArray>

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

    override var neighboursIndicesCache: Array<IntArray> = emptyArray()
}
