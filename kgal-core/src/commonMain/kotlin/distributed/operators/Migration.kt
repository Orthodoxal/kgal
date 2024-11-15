package kgal.distributed.operators

import kgal.distributed.DistributedLifecycle
import kgal.distributed.DistributedGA
import kgal.size
import kgal.utils.indicesByRandom
import kgal.utils.randomWithIndices

/**
 * Returns the minimal count as [percent] of subpopulation.
 */
private fun <V, F> DistributedLifecycle<V, F>.minCountByPercent(percent: Double) =
    children.fold(Int.MAX_VALUE) { acc, cluster ->
        val count = (cluster.population.size * percent).toInt()
        if (count < acc) count else acc
    }

/**
 * Executes random migration stage.
 * Migration in [DistributedGA] is a process of chromosomes exchange between subpopulations.
 * 1) selects some chromosomes in subpopulations
 * 2) migrate selected chromosomes with strategy:
 * `0 -> 1 -> 2 -> 3 -> ... -> N -> 0`, where `N` - count of [DistributedGA.children].
 * @param percent percentage of the population that will migrate.
 * If sizes of subpopulations is different, the percent of minimal size will be selected.
 * General count of chromosomes involved in migration is calculated using the formula:
 * `percent * min(subpopulations.sizes) * subpopulations.size`
 *
 * For example:
 *
 * [percent] is `0.1`, subpopulations count is `3`, subpopulation sizes are `[100, 100, 80]`, then:
 * count of chromosomes involved in migration =
 *
 * `0.1 * min(100, 100, 80) * 3 = 0.1 * 80 * 3 = 24` by `8` chromosomes from each subpopulation.
 */
public fun <V, F> DistributedLifecycle<V, F>.migration(percent: Double) {
    val count = minCountByPercent(percent)
    var (chromosomes, indices) = children.last().population.randomWithIndices(count, random)
    for (clusterIndex in children.lastIndex - 1 downTo 0) {
        val indicesNext = indicesByRandom(count, children[clusterIndex].size, random)
        indices.forEachIndexed { index, i ->
            children[clusterIndex + 1].population[i] = children[clusterIndex].population[indicesNext[index]]
            indices = indicesNext
        }
    }
    indices.forEachIndexed { index, i ->
        children[0].population[i] = chromosomes[index]
    }
}
