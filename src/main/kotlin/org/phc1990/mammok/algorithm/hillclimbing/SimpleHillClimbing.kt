package org.phc1990.mammok.algorithm.hillclimbing

import org.phc1990.mammok.optimization.*
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.optimization.InternalIteration
import org.phc1990.mammok.topology.neighborhood.RandomlySortedNeighborhood
import org.phc1990.mammok.topology.space.Space

class SimpleHillClimbing(private val maxIterations: Int? = null): Algorithm<Space<Any>> {

    override val name: String = "Simple Hill Climbing"
    private var variables: Array<Space<Any>> = arrayOf()
    private var objectives: Array<OptimizationCriterion> = arrayOf()

    override fun addVariable(space: Space<Any>): Int {
        variables += space
        return variables.size-1
    }

    override fun addObjective(criterion: OptimizationCriterion): Int {
        objectives += criterion
        return objectives.size-1
    }

    override fun run(evaluator: BlackBoxEvaluator, processor: IterationProcessor) {

        var i = 0
        var stop = false

        // Initialise point
        var best: Candidate = InternalCandidate.uniform(0, 0, variables, objectives.size)
        evaluator.evaluate(best)
        stop = processor.process(InternalIteration(0, stop, listOf(best)))
        while(!stop) {

            // Create neighborhood
            val neighborIterator = RandomlySortedNeighborhood(best, variables, objectives.size)
            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluator.evaluate(candidate)

                if (candidate.challenge(best, 0, objectives[0])) {
                    best = candidate
                    foundBetter = true
                    // We have already found a better candidate, neighbour search is stop
                    break
                }
            }

            maxIterations?.let { stop = (i >= maxIterations-1) }
            // If we have not found a better candidate in the neighbour hood, we are locally stuck
            if (!foundBetter) {stop = true}
            if (processor.process(InternalIteration(i, stop, listOf(best)))) {stop = true}
            i++
        }
    }

}