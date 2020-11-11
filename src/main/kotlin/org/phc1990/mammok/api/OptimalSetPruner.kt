package org.phc1990.mammok.api

interface OptimalSetPruner {
    val name: String
    fun prune(optimalSet: MutableSet<Candidate>)

}