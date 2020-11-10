package org.phc1990.mammok.optimization.optimalset.pruning

import org.phc1990.mammok.optimization.Candidate

object OptimalSetPrunerFactory {

    fun getNoPruner(): OptimalSetPruner {
        return object : OptimalSetPruner {
            override val name = "No pruner"
            override fun prune(mutableSet: MutableSet<Candidate>) {}
        }
    }
}