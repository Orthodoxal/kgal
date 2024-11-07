package kgal.utils

import kgal.cellular.neighborhood.VonNeumann

/**
 * The Delannoy number counts the cells in an m-dimensional [VonNeumann] neighborhood of radius n ([VonNeumann.radius]).
 * @see <a href="https://en.wikipedia.org/wiki/Delannoy_number">Delannoy number</a>
 */
internal fun delannoyNumber(m: Int, n: Int): Int {
    val dp = Array(m + 1) { IntArray(n + 1) }

    for (i in 0..m) {
        for (j in 0..n) {
            if (i == 0 || j == 0) {
                dp[i][j] = 1
            } else {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] + dp[i - 1][j - 1]
            }
        }
    }

    return dp[m][n]
}
