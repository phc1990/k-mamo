package org.phc1990.mammok.algorithm.hillclimbing
/*
import org.phc1990.mammok.optimization.*
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.algorithm.InternalIteration
import org.phc1990.mammok.optimization.VariableFactory
import org.phc1990.mammok.topology.neighborhood.RandomlySortedNeighborhood
import org.phc1990.mammok.topology.space.LinearSpace
import org.phc1990.mammok.topology.space.Space

class StochasticHillClimbing(private val objective: Objective,
                             private val selectionFunction: (improvement: Double) -> Boolean,
                             private val maxIterations: Int? = null): Algorithm<LinearSpace<*>> {

    override val name: String = "Stochastic Hill Climbing"
    private var variables: Array<Variable<*>> = arrayOf()
    private lateinit var best: Candidate

    fun <T> addVariable(name: String, space: Space<T>): Variable<T> =
            VariableFactory.get(name, space).also { variables += it }

    override fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor) {

        processor.initialize()

        var i = 0
        var stop = false
        while(!stop) {

            // Initialise best candidate
            if (best == null) {
                best =  InternalCandidate.uniform(0, 0, variables) }

            // Create neighborhood
            val neighborIterator = RandomlySortedNeighborhood(best.iterationIndex, best.variables)
            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluator.evaluate(candidate)

                if (candidate.challenge(best, objective)) {
                    foundBetter = selectionFunction.invoke(candidate.improvement(best, objective))
                    if (foundBetter) {
                        best = candidate
                        // We have already found a better candidate, neighbour search is stop
                        break
                    }
                }
            }

            maxIterations?.let { stop = (i >= maxIterations-1) }
            if (!foundBetter) {stop = true}
            if (processor.process(InternalIteration(i, stop, listOf(best)))) {stop = true}
            i++
        }
    }
}

*/