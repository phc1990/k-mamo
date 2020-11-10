package org.phc1990.mammok.optimization.optimalset

import org.phc1990.mammok.optimization.Candidate

internal class OptimalSet(val prevalence: (c1: Candidate, c2: Candidate) -> Int,
                          val pruning: (set: Set<Candidate>) -> Unit) {
    private var set: MutableSet<Candidate> = mutableSetOf()

    // TODO take a look at algorithm 19.4
    fun extract(candidates: Set<Candidate>) = candidates.forEach { update(it) }

    fun update(candidate: Candidate): Boolean {
        for (old in set) {
            val prevalence = prevalence(old, candidate)
            if (prevalence > 1) {
                set.remove(old)
            } else {
                return false
            }
        }
        set.add(candidate)
        return true
    }

    fun prune(): Set<Candidate> { pruning(set); return set() }
    fun set(): Set<Candidate> = set
}