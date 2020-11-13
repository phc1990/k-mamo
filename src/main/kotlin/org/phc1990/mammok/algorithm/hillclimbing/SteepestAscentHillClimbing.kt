package org.phc1990.mammok.algorithm.hillclimbing

import org.phc1990.mammok.algorithm.AbstractAlgorithm
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.algorithm.InternalIteration
import org.phc1990.mammok.api.*
import org.phc1990.mammok.optimization.optimalset.OptimalSet
import org.phc1990.mammok.topology.neighborhood.RandomlySortedNeighborhood
import org.phc1990.mammok.topology.space.Space

class SteepestAscentHillClimbing(private val maxIterations: Int? = null): AbstractAlgorithm<Space<Any>>() {

    override val name: String = "Steepest Ascent Hill Climbing"
    override fun run(evaluator: CandidateEvaluator,
                     comparator: CandidateComparator,
                     pruner: OptimalSetPruner,
                     processor: IterationProcessor) {

        var i = 0
        var stop = false

        // Initialise point
        var best: Candidate = InternalCandidate.uniform(0, variables)
        evaluator.evaluate(best)

        // Initialise optimal set
        val optimalSet = OptimalSet(comparator, pruner)
        optimalSet.extract(setOf(best))

        // Initialise process
        stop = processor.process(InternalIteration(0, stop, optimalSet.prune()))

        while(!stop) {

            // Create neighborhood
            val neighborIterator = RandomlySortedNeighborhood(optimalSet.set().first(), variables)
            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluator.evaluate(candidate)

                // Store whether a better candidate has been found, but keep exploring the entire neighborhood.
                if (optimalSet.update(candidate)) foundBetter = true
            }

            // Check iterations stop criterion
            maxIterations?.let { stop = (i >= maxIterations-1) }

            // If we have not found a better candidate in the neighbour hood, we are locally stuck
            if (!foundBetter) { stop = true }

            // Process iteration
            if (processor.process(InternalIteration(i, stop, optimalSet.prune()))) {stop = true}
            i++
        }
    }
}
