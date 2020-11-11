package org.phc1990.mammok.utils

import org.phc1990.mammok.api.CandidateComparator
import org.phc1990.mammok.optimization.optimalset.comparators.WeightedSumComparator

object CandidateComparators {

    fun getWeightedSum(vararg weights: Double): CandidateComparator = WeightedSumComparator(weights)



}