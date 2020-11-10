package org.phc1990.mammok.optimization.optimalset.pruning

import org.phc1990.mammok.optimization.Candidate

interface OptimalSetPruner {
    val name: String
    fun prune(mutableSet: MutableSet<Candidate>)
}