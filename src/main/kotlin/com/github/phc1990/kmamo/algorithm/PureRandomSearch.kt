package com.github.phc1990.kmamo.algorithm

import com.github.phc1990.kmamo.optimization.Objective
import com.github.phc1990.kmamo.optimization.Variable
import com.github.phc1990.kmamo.optimization.VariableFactory
import com.github.phc1990.kmamo.space.Space
import com.google.common.collect.ImmutableList
import kotlin.math.max

class PureRandomSearch(val maxIterations: Int): Algorithm {

    private val variables: MutableMap<Any, Any> = mutableMapOf()
    private val objectives: MutableList<Objective> = mutableListOf()
    private var iterationIndex = 0

    fun <T, S: Space<T>> addVariable(name: String, space: S): Variable<T> {
        val variable = VariableFactory.get(name, space)
        variables[variable] = space
        return variable
    }

    fun addObjective(objective: Objective): PureRandomSearch {
        objectives.add(objective)
        return this
    }

    override fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor) {

        for (i in 0..maxIterations) {

            val candidate = InternalCandidate(i, i,
                    variables.entries.associate { e -> Pair(e.key, (e.value as Space<Any>).uniform())})
            evaluator.evaluate(candidate)

            val iteration = InternalIteration(i, i == maxIterations - 1, listOf(candidate))
            processor.process(iteration)
        }


    }

}