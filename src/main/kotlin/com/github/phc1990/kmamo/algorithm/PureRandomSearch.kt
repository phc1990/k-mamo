package com.github.phc1990.kmamo.algorithm

import com.github.phc1990.kmamo.optimization.*
import com.github.phc1990.kmamo.optimization.ObjectiveFactory
import com.github.phc1990.kmamo.optimization.VariableFactory
import com.github.phc1990.kmamo.space.FiniteSpace
import com.github.phc1990.kmamo.space.MetricSpace
import com.github.phc1990.kmamo.space.Space
import com.google.common.collect.ImmutableList
import kotlin.math.max

class PureRandomSearch(val maxIterations: Int): Algorithm {

    private val variables: MutableMap<Any, Any> = mutableMapOf()
    private val objectives: MutableList<Objective> = mutableListOf()
    private var iterationIndex = 0
    private lateinit var best: InternalCandidate

    fun <T, S: FiniteSpace<T>> addVariable(name: String, space: S): Variable<T> =
        VariableFactory.get(name, space).also { variables[it] = space }

    fun addObjective(name: String, criterion: OptimizationCriterion): Objective =
        ObjectiveFactory.get(name, criterion).also { objectives.add(it) }

    override fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor) {

        for (i in 0 until maxIterations) {

            val candidate = InternalCandidate(i, i,
                    variables.entries.associate { e -> Pair(e.key, (e.value as Space<Any>).uniform())})
            evaluator.evaluate(candidate)

            if (this::best.isInitialized) {
                val candidateValue = candidate.objectives.values.toList()[0]
                val bestValue = best.objectives.values.toList()[0]
                if (candidateValue > bestValue) {best = candidate}
            } else {
                best = candidate
            }

            val iteration = InternalIteration(i, i == maxIterations - 1, listOf(best))
            processor.process(iteration)
        }


    }

}