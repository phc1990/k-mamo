package org.phc1990.mammok.algorithm.hillclimbing

import org.phc1990.mammok.algorithm.AbstractAlgorithm
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.algorithm.InternalIteration
import org.phc1990.mammok.api.*
import org.phc1990.mammok.optimization.optimalset.OptimalSet
import org.phc1990.mammok.topology.neighborhood.RandomlySortedNeighborhood
import org.phc1990.mammok.topology.space.Space

class StochasticHillClimbing(private val steepestAscent: Boolean,
                             private val maxStochasticRetries: Int,
                             private val maxIterations: Int? = null): AbstractAlgorithm<Space<Any>>() {

    override val name: String = if (steepestAscent)
        "Stochastic Steepest Ascent Hill Climbing" else "Stochastic Simple Hill Climbing"

    override fun run(evaluator: CandidateEvaluator,
                     comparator: CandidateComparator,
                     pruner: OptimalSetPruner,
                     processor: IterationProcessor) {

        // Initialisation
        var i = 0
        var j = 0
        val optimalSet = OptimalSet(comparator, pruner)
        var randomRestart = false

        InternalCandidate.uniform(searchSpaces).also {
            evaluator.evaluate(it)
            optimalSet.extract(setOf(it))
        }

        var stop = processor.process(InternalIteration(false, optimalSet.prune()))

        while(!stop) {

            // Create neighborhood
            val neighborIterator = if (randomRestart) {
                randomRestart = false
                RandomlySortedNeighborhood(InternalCandidate.uniform(searchSpaces), searchSpaces, true)
            } else RandomlySortedNeighborhood(optimalSet.set().first(), searchSpaces)

            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluator.evaluate(candidate)

                // Store whether a better candidate has been found.
                if (optimalSet.update(candidate)) {
                    foundBetter = true

                    // Simple Hill Climbing would stop the search here
                    if (!steepestAscent) { break }
                }
            }

            // Check iterations stop criterion
            maxIterations?.let { stop = (i >= maxIterations-1) }

            // If we have not found a better candidate we might trigger a random restart
            if (!foundBetter) {
                if (j == maxStochasticRetries) {stop = true} else {randomRestart = true}
                j++
            }

            // Process iteration
            if (processor.process(InternalIteration(stop, optimalSet.prune()))) break else i++
        }
    }
}