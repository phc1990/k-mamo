package org.phc1990.mammok.algorithm.hillclimbing

import org.phc1990.mammok.optimization.*
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.optimization.InternalIteration
import org.phc1990.mammok.optimization.VariableFactory
import org.phc1990.mammok.topology.neighborhood.RandomlySortedNeighborhood
import org.phc1990.mammok.topology.space.Space

class SimpleHillClimbing(private val maxIterations: Int? = null): Algorithm<Space<*>> {

    override val name: String = "Simple Hill Climbing"
    private var variables: Array<Space<*>> = arrayOf()
    private var objectives: Array<OptimizationCriterion> = arrayOf()
    private lateinit var best: Candidate


    override fun addVariable(space: Space<*>): Int {
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
        while(!stop) {

            // Initialise best candidate
            if (best == null) {
                best =  InternalCandidate.uniform(0, 0, variables, objectives.size) }

            // Create neighborhood
            val neighborIterator = RandomlySortedNeighborhood(best.iterationIndex, best.variables)
            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluator.evaluate(candidate)

                if (candidate.challenge(best, objective)) {
                    best = candidate
                    foundBetter = true
                    // We have already found a better candidate, neighbour search is stop
                    break
                }
            }

            maxIterations?.let { stop = (i >= maxIterations-1) }
            if (!foundBetter) {stop = true}
            if (processor.process(InternalIteration(i, stop, listOf(best)))) {stop = true}
            i++
        }
    }

}