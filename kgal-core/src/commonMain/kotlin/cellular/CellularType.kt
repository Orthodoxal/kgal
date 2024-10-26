package kgal.cellular

import kotlin.random.Random

public sealed interface CellularType {

    public data object Synchronous : CellularType

    public data class Asynchronous(val updatePolicy: UpdatePolicy = UpdatePolicy.LineSweep) : CellularType
}

public sealed interface UpdatePolicy {
    public data object LineSweep : UpdatePolicy
    public data object NewRandomSweep : UpdatePolicy
    public data object UniformChoice : UpdatePolicy
    public data class FixedRandomSweep(val random: Random) : UpdatePolicy {

        private var size = -1

        private var indicesShuffled: IntArray = intArrayOf()

        public fun cacheIndices(size: Int): IntArray {
            if (size != this.size) {
                this.size = size
                indicesShuffled = IntArray(size) { it }.apply { shuffle(random) }
            }
            return indicesShuffled
        }
    }
}
