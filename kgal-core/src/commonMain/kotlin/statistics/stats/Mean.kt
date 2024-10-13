package kgal.statistics.stats


import kgal.GA
import kgal.Lifecycle
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
public val Lifecycle<*, Int>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanDouble")
public val Lifecycle<*, Double>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Mean fitness value of chromosomes in [Population]
 */
@get:JvmName("getMeanLong")
public val Lifecycle<*, Long>.mean: Double
    get() = population.fold(0.0) { acc, chromosome -> acc + chromosome.fitness!! } / size

/**
 * Creates [Statistic] for mean fitness value of chromosomes in [Population]
 */
@JvmName("meanInt")
public fun Lifecycle<*, Int>.mean(): Statistic<Double> = Statistic(NAME, mean)

/**
 * Creates [Statistic] for mean fitness value of chromosomes in [Population]
 */
@JvmName("meanDouble")
public fun Lifecycle<*, Double>.mean(): Statistic<Double> = Statistic(NAME, mean)

/**
 * Creates [Statistic] for mean fitness value of chromosomes in [Population]
 */
@JvmName("meanLong")
public fun Lifecycle<*, Long>.mean(): Statistic<Double> = Statistic(NAME, mean)
