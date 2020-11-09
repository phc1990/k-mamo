package org.phc1990.mammok.optimization.prevalence

import org.phc1990.mammok.optimization.Candidate

/**
 * Prevalence comparator.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 9, 2020
 */
object PrevalenceComparatorFactory {

    /**
     * Returns a prevalence comparator based on the weighted sum of each of the candidate objective values.
     */
    fun weightedSum(vararg weights: Double): PrevalenceComparator {
        return object : PrevalenceComparator {
            override val name: String = "Weighted Sum"
            override fun compare(o1: Candidate, o2: Candidate): Int {
                var s1 = 0.0
                var s2 = 0.0
                for (i in o1.objectives.indices) {
                    weights[i].also {w ->
                        s1 += w * o1.objectives[i]
                        s2 += w * o2.objectives[i]
                    }
                }
                return s1.compareTo(s2)
            }
        }
    }
}