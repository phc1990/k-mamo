package org.phc1990.mammok.algorithm.hillclimbing

import org.phc1990.mammok.algorithm.AbstractAlgorithm
import org.phc1990.mammok.optimization.*
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.optimization.InternalIteration
import org.phc1990.mammok.optimization.optimalset.OptimalSet
import org.phc1990.mammok.topology.neighborhood.RandomlySortedNeighborhood
import org.phc1990.mammok.topology.space.Space

class SimpleHillClimbing(private val maxIterations: Int? = null): AbstractAlgorithm<Space<Any>>() {

    override val name: String = "Simple Hill Climbing"

    override fun run(evaluation: (candidate: Candidate) -> Unit, prevalence: (c1: Candidate, c2: Candidate) -> Int,
                     pruning: (set: Set<Candidate>) -> Unit, process: (iteration: Iteration) -> Boolean) {

        var i = 0
        var stop = false

        // Initialise point
        var best: Candidate = InternalCandidate.uniform(0, variables)
        evaluation(best)

        // Initialise optimal set
        val optimalSet = OptimalSet(prevalence, pruning)
        optimalSet.extract(setOf(best))

        // Initialise process
        stop = process(InternalIteration(0, stop, optimalSet.prune()))

        while(!stop) {

            // Create neighborhood
            val neighborIterator = RandomlySortedNeighborhood(best, variables)
            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluation(candidate)

                foundBetter = optimalSet.update(candidate)
                // We have already found a better candidate, neighbour search is stop
                if (foundBetter) { break }
            }

            // Check iterations stop criterion
            maxIterations?.let { stop = (i >= maxIterations-1) }

            // If we have not found a better candidate in the neighbour hood, we are locally stuck
            if (!foundBetter) { stop = true }

            // Process iteration
            if (process(InternalIteration(i, stop, optimalSet.prune()))) {stop = true}
            i++
        }
    }


}