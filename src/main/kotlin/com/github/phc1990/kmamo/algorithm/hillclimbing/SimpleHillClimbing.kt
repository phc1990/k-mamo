package com.github.phc1990.kmamo.algorithm.hillclimbing

import com.github.phc1990.kmamo.optimization.*
import com.github.phc1990.kmamo.optimization.InternalCandidate
import com.github.phc1990.kmamo.optimization.InternalIteration
import com.github.phc1990.kmamo.optimization.VariableFactory
import com.github.phc1990.kmamo.topology.LinearSpace
import com.github.phc1990.kmamo.topology.NeighborSpace
import com.github.phc1990.kmamo.topology.Space

class SimpleHillClimbing(private val objective: Objective, private val maxIterations: Int? = null): Algorithm {

    override val name: String = "Simple Hill Climbing"
    private val variables: MutableMap<Variable<*>, Any> = mutableMapOf()
    private lateinit var best: Candidate

    fun <T, S: Space<T>> addVariable(name: String, space: S): Variable<T> =
            VariableFactory.get(name, space).also { variables[it] = space }

    override fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor) {

        var i = 0
        var stop = false
        while(!stop) {

            // Initialise best candidate
            if (best == null) {
                best =  InternalCandidate.uniform(0, 0, variables) }

            // Spawn new neighbour iterator
            val neighborIterator = spawnNeighborIterator(best)
            var foundBetter = false

            while(neighborIterator.hasNext()) {

                val candidate = neighborIterator.next()
                evaluator.evaluate(candidate)

                if (candidate.challenge(best, objective)) {
                    best = candidate
                    foundBetter = true
                }
            }

            // Process iteration
            maxIterations?.let { stop = (i >= maxIterations-1) }
            if (foundBetter) {stop = true}
            if (processor.process(InternalIteration(i, stop, listOf(best)))) {stop = true}
            i++
        }
    }

    protected abstract fun spawnNeighborIterator(node: Candidate): Iterator<Candidate>
}