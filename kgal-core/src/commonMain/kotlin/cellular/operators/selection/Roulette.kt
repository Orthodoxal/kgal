package kgal.cellular.operators.selection

import kgal.cellular.CellEvolveScope
import kgal.chromosome.Chromosome
import kgal.operators.selection.selectionRoulette
import kotlin.jvm.JvmName

/**
 * Executes roulette selection step for [CellEvolveScope.neighbors]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 */
@JvmName("selRouletteFitInt")
public fun <V> CellEvolveScope<V, Int>.selRoulette(): Unit = selection { source ->
    val totalFitness = source.fold(0L) { acc: Long, chromosome: Chromosome<V, Int> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selectionRoulette(source, totalFitness, random)
}

/**
 * Executes roulette selection step for [CellEvolveScope.neighbors]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 */
@JvmName("selRouletteFitLong")
public fun <V> CellEvolveScope<V, Long>.selRoulette(): Unit = selection { source ->
    val totalFitness = source.fold(0L) { acc: Long, chromosome: Chromosome<V, Long> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selectionRoulette(source, totalFitness, random)
}

/**
 * Executes roulette selection step for [CellEvolveScope.neighbors]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 */
@JvmName("selRouletteFitShort")
public fun <V> CellEvolveScope<V, Short>.selRoulette(): Unit = selection { source ->
    val totalFitness = source.fold(0L) { acc: Long, chromosome: Chromosome<V, Short> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selectionRoulette(source, totalFitness, random)
}

/**
 * Executes roulette selection step for [CellEvolveScope.neighbors]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 */
@JvmName("selRouletteFitByte")
public fun <V> CellEvolveScope<V, Byte>.selRoulette(): Unit = selection { source ->
    val totalFitness = source.fold(0L) { acc: Long, chromosome: Chromosome<V, Byte> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selectionRoulette(source, totalFitness, random)
}

/**
 * Executes roulette selection step for [CellEvolveScope.neighbors]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 */
@JvmName("selRouletteFitDouble")
public fun <V> CellEvolveScope<V, Double>.selRoulette(): Unit = selection { source ->
    val totalFitness = source.fold(0.0) { acc: Double, chromosome: Chromosome<V, Double> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selectionRoulette(source, totalFitness, random)
}

/**
 * Executes roulette selection step for [CellEvolveScope.neighbors]:
 *
 * It is assumed that all individuals are "placed" on the roulette wheel.
 *
 * For example, if you have 4 individuals with fitness 1, 2, 3, and 4,
 * each of them will have its own "share" of the total 10 (1/10, 2/10, 3/10, and 4/10).
 * A random number is generated between 0 and the total fitness (e.g. 10).
 * The roulette wheel is spun and the random number "falls" on a sector,
 * which corresponds to one of the individuals that will be selected.
 */
@JvmName("selRouletteFitFloat")
public fun <V> CellEvolveScope<V, Float>.selRoulette(): Unit = selection { source ->
    val totalFitness = source.fold(0f) { acc: Float, chromosome: Chromosome<V, Float> ->
        acc + (chromosome.fitness ?: error("Fitness is null"))
    }
    selectionRoulette(source, totalFitness, random)
}
