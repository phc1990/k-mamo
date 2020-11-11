package org.phc1990.mammok.optimization.optimalset.comparators

import org.phc1990.mammok.api.CandidateComparator
import org.phc1990.mammok.api.Candidate

/**
 * Prevalence comparator.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 9, 2020
 */
class WeightedSumComparator(private val weights: DoubleArray): CandidateComparator {

    override val name: String = "Weighted Sum"

    override fun prevalence(c1: Candidate, c2: Candidate): Int {
        var s1 = 0.0
        var s2 = 0.0
        for (i in c1.objectives.indices) { weights[i].also {s1 += it * c1.objectives[i]; s2 += it * c2.objectives[i] }}
        return s1.compareTo(s2)
    }
}
