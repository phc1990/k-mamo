package com.github.phc1990.kmamo.algorithm.hillclimbing

import com.github.phc1990.kmamo.optimization.*
import com.github.phc1990.kmamo.optimization.InternalCandidate
import com.github.phc1990.kmamo.optimization.InternalIteration
import com.github.phc1990.kmamo.optimization.VariableFactory
import com.github.phc1990.kmamo.space.LinearSpace
import com.github.phc1990.kmamo.space.NeighborSpace

abstract class AbstractHillClimbing(private val objective: Objective, private val maxIterations: Int? = null): Algorithm {

    private val variables: MutableMap<Variable<*>, Any> = mutableMapOf()
    private val neighbourVariables: MutableMap<Variable<*>, NeighborSpace<*>> = mutableMapOf()
    private val linearVariables: MutableMap<Variable<*>, Pair<LinearSpace<*>, Double>> = mutableMapOf()
    private lateinit var best: Candidate

    fun <T, S: NeighborSpace<T>> addVariable(name: String, space: S): Variable<T> =
            VariableFactory.get(name, space).also { neighbourVariables[it] = space; variables[it] = space }

    fun <T, S: LinearSpace<T>> addVariable(name: String, space: S, step: Double) =
            VariableFactory.get(name, space).also { linearVariables[it] = Pair(space, step); variables[it] = space }

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