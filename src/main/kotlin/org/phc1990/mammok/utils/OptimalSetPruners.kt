package org.phc1990.mammok.utils

import org.phc1990.mammok.api.OptimalSetPruner
import org.phc1990.mammok.api.Candidate

object OptimalSetPruners {

    fun getNone(): OptimalSetPruner = object : OptimalSetPruner {
        override val name = "None"
        override fun prune(optimalSet: MutableSet<Candidate>) {}
    }

}