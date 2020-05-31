package com.github.phc1990.kmamo.algorithm

import com.github.phc1990.kmamo.optimization.Objective
import com.github.phc1990.kmamo.optimization.Variable
import com.github.phc1990.kmamo.space.Space
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap

interface Algorithm {

    fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor)

    fun solve(evaluator: (candidate: UnevaluatedCandidate) -> Unit,
              processor: (iteration: Iteration) -> Unit) {

        val anonymousEvaluator = object : BlackBoxEvaluator {
            override fun evaluate(candidate: UnevaluatedCandidate) = evaluator.invoke(candidate)
        }

        val anonymousProcessor = object : IterationProcessor {
            override fun process(iteration: Iteration) = processor.invoke(iteration)
        }

        solve(anonymousEvaluator, anonymousProcessor)
    }
}

interface Candidate {
    val evaluationIndex: Int
    val iterationIndex: Int
    fun <T> getVariable(variable: Variable<T>): T
}

interface UnevaluatedCandidate: Candidate {
    fun setObjective(objective: Objective, value: Double)
}

interface EvaluatedCandidate: Candidate {
    fun getObjective(objective: Objective): Double
}

interface Iteration {
    val index: Int
    val stopped: Boolean
    val points: List<EvaluatedCandidate>
    fun size(): Int = points.size
}

interface BlackBoxEvaluator {
    fun evaluate(candidate: UnevaluatedCandidate)
}

interface IterationProcessor {
    fun process(iteration: Iteration)
}

internal class InternalCandidate(override val evaluationIndex: Int, override val iterationIndex: Int,
                                 private val variables: Map<Any, Any>): UnevaluatedCandidate, EvaluatedCandidate {

    private val objectives: MutableMap<Objective, Double> = mutableMapOf()
    override fun <T> getVariable(variable: Variable<T>): T = variables[variable] as T
    override fun setObjective(objective: Objective, value: Double) { objectives[objective] = value }
    override fun getObjective(objective: Objective): Double = objectives[objective] as Double
}

internal class InternalIteration(override val index: Int, override val stopped: Boolean,
                                 override val points: List<EvaluatedCandidate>): Iteration

