package kgal.cellular

import kgal.AbstractGA
import kgal.GA

public interface CellularGA<V, F> : GA<V, F> {
    override val population: CellularPopulation<V, F>
    public var elitism: Boolean
    public var cellularType: CellularType
    public var neighborhood: CellularNeighborhood

    public companion object {

        public fun <V, F> create(
            configuration: CellularConfig<V, F>,
        ): CellularGA<V, F> = CellularGAInstance(configuration)
    }
}

public inline fun <V, F> cGA(
    population: CellularPopulation<V, F>,
    noinline fitnessFunction: (V) -> F,
    config: CellularConfigScope<V, F>.() -> Unit,
): CellularGA<V, F> =
    CellularGA.create(
        configuration = CellularConfigScope(population, fitnessFunction).apply(config)
    )

internal class CellularGAInstance<V, F>(
    configuration: CellularConfig<V, F>,
) : CellularGA<V, F>, AbstractGA<V, F, CellularLifecycle<V, F>>(configuration) {

    override val population: CellularPopulation<V, F> = configuration.population

    override val lifecycle: CellularLifecycle<V, F> by lazy { CellularLifecycle(this, configuration) }

    override var elitism: Boolean = configuration.elitism

    override var cellularType: CellularType = configuration.cellularType

    override var neighborhood: CellularNeighborhood = configuration.neighborhood
        set(value) {
            //if (field != value) lifecycle.store[IS_CACHE_NEIGHBORHOOD_ACTUAL] = false
            field = value
        }
}
