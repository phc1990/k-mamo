package org.phc1990.mammok.optimization.optimalset.prevalence

import org.phc1990.mammok.optimization.Candidate

/**
 * Prevalence comparator.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 9, 2020
 */
object Prevalence {

    /**
     * Returns a prevalence comparator based on the weighted sum of each of the candidate objective values.
     */
    fun getWeightedSum(vararg weights: Double): (c1: Candidate, c2: Candidate) -> Int = { c1, c2 ->

            var s1 = 0.0
            var s2 = 0.0
            for (i in c1.objectives.indices) {
                weights[i].also {w ->
                    s1 += w * c1.objectives[i]
                    s2 += w * c2.objectives[i]
                }
            }
            s1.compareTo(s2)
        }

    }
}