package kgal.statistics.note

import kgal.GA

/**
 * [StatisticNote] - general abstraction for a statistical record that includes:
 * - statistical data (calculated statistical information)
 * - owner's name (name of population on which the calculation was performed)
 * - iteration ([GA.iteration] on which the calculation was performed)
 *
 * @see SingleStatisticNote
 * @see MultiStatisticNote
 * @see Statistic
 */
public interface StatisticNote<V> {

    /**
     * Statistical data (calculated statistical information)
     */
    public val statistic: Statistic<V>

    /**
     * Owner's name (name of population on which the calculation was performed)
     */
    public val ownerName: String

    /**
     * Iteration ([GA.iteration] on which the calculation was performed)
     */
    public val iteration: Int
}
