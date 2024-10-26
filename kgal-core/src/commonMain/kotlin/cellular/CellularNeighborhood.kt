package kgal.cellular


public interface CellularNeighborhood {

    public val radius: Int

    public fun neighboursCount(dimenCount: Int): Int

    public fun neighboursIndicesMatrix(dimens: Dimens): Pair<IntArray, Array<IntArray>>
}
