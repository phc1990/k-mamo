package org.phc1990.mammok.optimization.optimalset

import org.phc1990.mammok.api.Candidate
import org.phc1990.mammok.api.CandidateComparator
import org.phc1990.mammok.api.OptimalSetPruner

internal class OptimalSet(private val comparator: CandidateComparator,
                          private val pruner: OptimalSetPruner) {
    private var set: MutableSet<Candidate> = mutableSetOf()

    // TODO take a look at algorithm 19.4
    fun extract(candidates: Set<Candidate>) = candidates.forEach { update(it) }

    fun update(candidate: Candidate): Boolean {
        for (old in set) {
            val prevalence = comparator.compare(old, candidate)
            if (prevalence > 0) {
                set.remove(old)
            } else {
                return false
            }
        }
        set.add(candidate)
        return true
    }

    fun prune(): Set<Candidate> { pruner.prune(set); return set() }
    fun set(): Set<Candidate> = set
}