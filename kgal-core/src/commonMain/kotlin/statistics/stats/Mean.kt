package kgal.statistics.stats


import kgal.EvolveScope
import kgal.GA
import kgal.Population
import kgal.size
import kgal.statistics.note.Statistic
import kotlin.jvm.JvmName

private const val NAME = "MEAN"

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanInt")
public val GA<*, Int>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanDouble")
public val GA<*, Double>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanLong")
public val GA<*, Long>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanInt")
public val EvolveScope<*, Int>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanDouble")
public val EvolveScope<*, Double>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanLong")
public val EvolveScope<*, Long>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Creates [Statistic] for mean fitness value of chromosomes in [Population]
 */
@JvmName("meanInt")
public fun EvolveScope<*, Int>.mean(): Statistic<Double> = Statistic(NAME, mean)

/**
 * Creates [Statistic] for mean fitness value of chromosomes in [Population]
 */
@JvmName("meanDouble")
public fun EvolveScope<*, Double>.mean(): Statistic<Double> = Statistic(NAME, mean)

/**
 * Creates [Statistic] for mean fitness value of chromosomes in [Population]
 */
@JvmName("meanLong")
public fun EvolveScope<*, Long>.mean(): Statistic<Double> = Statistic(NAME, mean)
