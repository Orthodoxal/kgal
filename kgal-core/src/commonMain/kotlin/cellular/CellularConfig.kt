package kgal.cellular

import kgal.AbstractConfigScope
import kgal.Config
import kgal.cellular.neighborhood.Moore
import kgal.chromosome.Chromosome
import kgal.dsl.ConfigDslMarker
import kotlin.random.Random

public interface CellularConfig<V, F> : Config<V, F, CellularLifecycle<V, F>> {

    public val elitism: Boolean

    public val cellularType: CellularType

    public val neighborhood: CellularNeighborhood

    override val population: CellularPopulation<V, F>
}

/**
 * Implementation of [CellularConfig] based on [AbstractConfigScope].
 * Params [population] and [fitnessFunction] are considered mandatory.
 */
public class CellularConfigScope<V, F>(
    override val population: CellularPopulation<V, F>,
    override var fitnessFunction: (V) -> F,
) : CellularConfig<V, F>, AbstractConfigScope<V, F, CellularLifecycle<V, F>>() {

    override var elitism: Boolean = true

    override var cellularType: CellularType = CellularType.Synchronous

    override var neighborhood: CellularNeighborhood = Moore(radius = 1)

    override val baseBefore: suspend CellularLifecycle<V, F>.() -> Unit = {
        // TODO доделать!!!
        /*fillPopulationIfEmpty()
        cacheNeighborhood()
        evaluationAll()*/
    }

    override var beforeEvolution: suspend CellularLifecycle<V, F>.() -> Unit = baseBefore

    public fun CellularConfigScope<V, F>.evolveCell(
        parallelismLimit: Int = parallelismConfig.workersCount,
        beforeLifecycleIteration: (suspend CellularLifecycle<V, F>.() -> Unit)?,
        afterLifecycleIteration: (suspend CellularLifecycle<V, F>.() -> Unit)?,
        cellLifecycle: suspend (@ConfigDslMarker CellLifecycle<V, F>).() -> Unit,
    ): Unit = evolveCellNoWrap(
        parallelismLimit = parallelismLimit,
        beforeLifecycleIteration = beforeLifecycleIteration,
        afterLifecycleIteration = afterLifecycleIteration,
    ) { chromosome, neighbours, random ->
        with(cellLifecycle(chromosome, neighbours, random)) { cellLifecycle(); cellChromosome }
    }

    public fun CellularConfigScope<V, F>.evolveCellNoWrap(
        parallelismLimit: Int = parallelismConfig.workersCount,
        beforeLifecycleIteration: (suspend CellularLifecycle<V, F>.() -> Unit)?,
        afterLifecycleIteration: (suspend CellularLifecycle<V, F>.() -> Unit)?,
        cellLifecycle: suspend (@ConfigDslMarker CellularLifecycle<V, F>).(chromosome: Chromosome<V, F>, neighbours: Array<Chromosome<V, F>>, random: Random) -> Chromosome<V, F>,
    ): Unit = evolve(
        useDefault = true,
        evolution = buildCellularLifecycle(
            parallelismLimit = parallelismLimit,
            beforeLifecycleIteration = beforeLifecycleIteration,
            afterLifecycleIteration = afterLifecycleIteration,
            cellLifecycle = cellLifecycle,
        ),
    )
}
