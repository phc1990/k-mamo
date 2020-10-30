package com.github.phc1990.mammok.algorithm.hillclimbing

import com.github.phc1990.mammok.optimization.*
import com.github.phc1990.mammok.optimization.InternalCandidate
import com.github.phc1990.mammok.optimization.InternalIteration
import com.github.phc1990.mammok.optimization.VariableFactory
import com.github.phc1990.mammok.topology.Neighborhood
import com.github.phc1990.mammok.topology.Space

class SimpleHillClimbing(private val objective: Objective, private val maxIterations: Int? = null): Algorithm {

    override val name: String = "Simple Hill Climbing"
    private val variables: MutableList<Variable<*>> = mutableListOf()
    private lateinit var best: InternalCandidate

    fun <T> addVariable(name: String, space: Space<T>): Variable<T> =
            VariableFactory.get(name, space).also { variables.add(it) }

    override fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor) {

        var i = 0
        var stop = false
        while(!stop) {

            // Initialise best candidate
            if (best == null) {
                best =  InternalCandidate.uniform(0, 0, variables) }

            // Create neighborhood
            val neighborIterator = Neighborhood(best)
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