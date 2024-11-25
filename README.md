# KGAL - Kotlin Genetic Algorithm Library 

[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://img.shields.io/maven-central/v/io.github.orthodoxal/kgal-core/0.0.5)](https://central.sonatype.com/artifact/io.github.orthodoxal/kgal-core/0.0.5)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-blue.svg?logo=kotlin)](http://kotlinlang.org)

## One Max Problem Example
```kotlin
 pGA( // PanmicticGA (Classical GA)
    population = population(size = 200) { booleans(size = 100) },
    fitnessFunction = { value -> value.count { it } },
 ) { 
    random = Random(seed = 42)
    elitism = 10

    before {
        println("GA STARTED, Init population: $population")
    }

    // create evolution strategy
    evolve {
        selTournament(size = 3) // select
        cxOnePoint(chance = 0.8) // crossover
        mutFlipBit(chance = 0.2, flipBitChance = 0.01) // mutate
        evaluation() // evaluate population
        stopBy(maxIteration = 50) { bestFitness == 100 } // finish GA by conditions
    }

    after {
        println("GA FINISHED, Result = $best")
    }
 }.startBlocking()
```

## Main Features

* Panmictic (Classical) Genetic Algorithm ([PanmicticGA])
  * Dynamic population size
  * [Panmictic elitism]
* Cellular Genetic Algorithm ([CellularGA])
  * [VonNeumann] and [Moore] neighborhoods
  * [Synchronous] and [Asynchronous] types
  * [Multidimensional] cellular population based on [Toroidal Shape]
  * [Cellular elitism]
* Distributed Genetic Algorithm ([DistributedGA]), include [Island Genetic Algorithm]
  * Support [child genetic algorithms] (more than subpopulations)
  * Synchronization child [GA]s (launching, shared statistics, shared population)
* Built-in genetic operators: `selection`, `crossover`, `mutation`, `evaluation`, `migration` ([DistributedGA] only)
* Flexible Customization
  * Typed [Chromosome] (individuals), built-in variants
  * Simple custom genetic operators based on low-api level
  * Full control of the evolutionary process: mutable population, mutable fitness function, stops any moment, dynamic evolution strategies
* [Parallelism] support for all types of GAs powered by [Kotlin Coroutines]:
  * [Panmictic parallelism]
  * [Cellular parallelism]
  * [Distributed parallelism]
* Statistics powered by [Kotlin Flows]
  * Built-in best, worst, mean, median, time, size [stats]
  * [Sessions statistics]
* Explicit [GA] Api powered by [Kotlin Coroutines]: `start()`, `stop()`, `resume()`, `restart()`
* [Kotlin DSL] style

## Examples
* [api](examples/src/commonMain/kotlin/api) &mdash; examples of using api kgal capabilities
  * [controller](examples/src/commonMain/kotlin/api/controller) &mdash; using interactive api ([GA.start], [GA.resume], [GA.stop], [GA.restart])
  * [customization](examples/src/commonMain/kotlin/api/customization) &mdash; GA customization possibilities for various tasks
  * [population](examples/src/commonMain/kotlin/api/population) &mdash; creating populations for GA
  * [statistics](examples/src/commonMain/kotlin/api/statistics) &mdash; working with statistics
  * [api capabilities](examples/src/commonMain/kotlin/api/ApiCapabilities.kt) &mdash; great example of solving the One Max Problem using various kgal api capabilities
* [tasks](examples/src/commonMain/kotlin/tasks) &mdash; a collection of examples of solved problems using kgal
  * [one max problem](examples/src/commonMain/kotlin/tasks/oneMax) &mdash; solving of the most famous problem for GA
  * [extremum search](examples/src/commonMain/kotlin/tasks/extremumSearch) &mdash; a collection of problems of finding extrema of complex functions
  * [single source shortest paths](examples/src/commonMain/kotlin/tasks/sssp) &mdash; solution to the SSSP problem by GA
  * [travelling salesman problem](examples/src/commonMain/kotlin/tasks/tsp) &mdash; solution to the TSP by GA + comparison with dynamic programming

## Using in your projects

### Maven

Add dependencies (you can also add other modules that you need):

```xml
<dependency>
    <groupId>io.github.orthodoxal</groupId>
    <artifactId>kgal-core</artifactId>
    <version>0.0.5</version>
</dependency>
```

And make sure that you use the latest Kotlin version:

```xml
<properties>
    <kotlin.version>2.0.20</kotlin.version>
</properties>
```

### Gradle

Add dependencies (you can also add other modules that you need):

```kotlin
dependencies {
    implementation("io.github.orthodoxal:kgal-core:0.0.5")
}
```

And make sure that you use the latest Kotlin version:

```kotlin
plugins {
    // For build.gradle.kts (Kotlin DSL)
    kotlin("jvm") version "2.0.20"
    
    // For build.gradle (Groovy DSL)
    id "org.jetbrains.kotlin.jvm" version "2.0.20"
}
```

Make sure that you have `mavenCentral()` in the list of repositories:

```kotlin
repositories {
    mavenCentral()
}
```

### Multiplatform

KGAL fully supports `Kotlin Multiplatform`.

In common code that should get compiled for different platforms, you can add a dependency to `kgal-core` right to the `commonMain` source set:

```kotlin
commonMain {
    dependencies {
        implementation("io.github.orthodoxal:kgal-core:0.0.5")
    }
}
```

## Documentation
See [documentation](https://orthodoxal.github.io/kgal/index.html) generated by `dokka`.

<!--- Examples -->
[GA.start]: https://orthodoxal.github.io/kgal/kgal-core/kgal/-g-a/start.html
[GA.resume]: https://orthodoxal.github.io/kgal/kgal-core/kgal/-g-a/resume.html
[GA.stop]: https://orthodoxal.github.io/kgal/kgal-core/kgal/-g-a/restart.html
[GA.restart]: https://orthodoxal.github.io/kgal/kgal-core/kgal/-g-a/stop.html

<!--- Features -->

<!--- PanmicticGA -->

[PanmicticGA]: https://orthodoxal.github.io/kgal/kgal-core/kgal.panmictic/-panmictic-g-a/index.html
[Panmictic elitism]: https://orthodoxal.github.io/kgal/kgal-core/kgal.panmictic/-panmictic-g-a/elitism.html

<!--- CellularGA -->

[CellularGA]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular/-cellular-g-a/index.html
[VonNeumann]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular.neighborhood/-von-neumann/index.html
[Moore]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular.neighborhood/-moore/index.html
[Synchronous]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular/-cellular-type/-synchronous/index.html
[Asynchronous]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular/-cellular-type/-asynchronous/index.html
[Multidimensional]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular/-dimens/index.html
[Toroidal Shape]: https://en.wikipedia.org/wiki/Toroid
[Cellular elitism]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular/-cellular-g-a/elitism.html

<!--- DistributedGA -->

[DistributedGA]: https://orthodoxal.github.io/kgal/kgal-core/kgal.distributed/-distributed-g-a/index.html
[Island Genetic Algorithm]: https://algorithmafternoon.com/genetic/island_genetic_algorithms/
[child genetic algorithms]: https://orthodoxal.github.io/kgal/kgal-core/kgal.distributed/-distributed-g-a/children.html
[GA]: https://orthodoxal.github.io/kgal/kgal-core/kgal/-g-a/index.html

<!--- Built-in genetic operators -->

<!--- Flexible Customization -->

[Chromosome]: https://orthodoxal.github.io/kgal/kgal-core/kgal.chromosome/-chromosome/index.html

<!--- Parallelism support -->

[Parallelism]: https://orthodoxal.github.io/kgal/kgal-core/kgal.processor.parallelism/-parallelism-config/index.html
[Kotlin Coroutines]: https://kotlinlang.org/docs/coroutines-overview.html
[Panmictic parallelism]: https://orthodoxal.github.io/kgal/kgal-core/kgal.panmictic/parallelism-config.html
[Cellular parallelism]: https://orthodoxal.github.io/kgal/kgal-core/kgal.cellular/parallelism-config.html
[Distributed parallelism]: https://orthodoxal.github.io/kgal/kgal-core/kgal.distributed/parallelism-config.html

<!--- Statistics -->

[Kotlin Flows]: https://kotlinlang.org/docs/flow.html#flows
[stats]: https://orthodoxal.github.io/kgal/kgal-core/kgal.statistics.stats/index.html
[Sessions statistics]: https://orthodoxal.github.io/kgal/kgal-core/kgal.statistics/-session/index.html

<!--- Explicit GA Api -->

<!--- Kotlin DSL -->

[Kotlin DSL]: https://kotlinlang.org/docs/type-safe-builders.html

