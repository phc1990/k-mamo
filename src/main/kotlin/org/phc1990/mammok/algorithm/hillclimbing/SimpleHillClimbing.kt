package org.phc1990.mammok.algorithm.hillclimbing

import org.phc1990.mammok.algorithm.AbstractAlgorithm
import org.phc1990.mammok.algorithm.Algorithm
import org.phc1990.mammok.optimization.*
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.optimization.InternalIteration
import org.phc1990.mammok.topology.neighborhood.RandomlySortedNeighborhood
import org.phc1990.mammok.topology.space.Space

class SimpleHillClimbing(private val maxIterations: Int? = null): AbstractAlgorithm<Space<Any>>() {

    override val name: String = "Simple Hill Climbing"

    override fun run(evaluate: (candidate: Candidate) -> Unit,
                     prevalence: (o1: Candidate, o2: Candidate) -> Int,
                     process: (iteration: Iteration) -> Boolean) {

        var i = 0
        var stop = false

        // Initialise point
        var best: Candidate = InternalCandidate.uniform(0, 0, variables)
        evaluate(best)
        stop = process(InternalIteration(0, stop, listOf(best)))
        while(!stop) {

            // Create neighborhood
            val neighborIterator = RandomlySortedNeighborhood(best, variables)
            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluate(candidate)

                if (prevalence(best, candidate) < 0) {
                    best = candidate
                    foundBetter = true
                    // We have already found a better candidate, neighbour search is stop
                    break
                }
            }

            maxIterations?.let { stop = (i >= maxIterations-1) }
            // If we have not found a better candidate in the neighbour hood, we are locally stuck
            if (!foundBetter) {stop = true}
            if (process(InternalIteration(i, stop, listOf(best)))) {stop = true}
            i++
        }
    }

}