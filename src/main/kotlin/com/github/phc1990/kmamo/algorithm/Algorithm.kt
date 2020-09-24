package com.github.phc1990.kmamo.algorithm

import com.github.phc1990.kmamo.optimization.Objective
import com.github.phc1990.kmamo.optimization.Variable
import com.github.phc1990.kmamo.space.Space

/** Algorithm. */
interface Algorithm {

    /** Solves the problem. */
    fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor)

    /** Solves the problem. */
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

/** A candidate solution. */
interface Candidate {

    /** The index of this instance amongst the other instances within the same iteration. */
    val index: Int

    /** The algorithm iteration to which this instance belongs. */
    val iterationIndex: Int

    /** Returns the value assigned to the variable. */
    fun <T> getVariable(variable: Variable<T>): T
}

/** A candidate that allows objective evaluation. */
interface UnevaluatedCandidate: Candidate {

    /** Sets the value of the given objective. */
    fun setObjective(objective: Objective, value: Double)
}

/** A candidate that has been evaluated. */
interface EvaluatedCandidate: Candidate {

    /** Returns the value of the given objective. */
    fun getObjective(objective: Objective): Double
}

/** An algorithm iteration. */
interface Iteration {

    /** The index of the instance. */
    val index: Int

    /** Flag indicating whether the stop criterion was met. */
    val stopped: Boolean

    /** List of evaluated candidates that constitute this iteration. */
    val candidates: List<EvaluatedCandidate>

    /** Side of the evaluated candidates. */
    fun size(): Int = candidates.size
}

/** 'Black-Box' evaluator used to evaluate candidates. */
interface BlackBoxEvaluator {
    fun evaluate(candidate: UnevaluatedCandidate)
}

/** Iteration processor used to handle algorithm iterations. */
interface IterationProcessor {
    fun process(iteration: Iteration)
}

/** Internal candidate implementation. */
internal class InternalCandidate(override val index: Int, override val iterationIndex: Int,
                                 private val variables: Map<Any, Any>): UnevaluatedCandidate, EvaluatedCandidate {

    internal val objectives: MutableMap<Objective, Double> = mutableMapOf()
    override fun <T> getVariable(variable: Variable<T>): T = variables[variable] as T
    override fun setObjective(objective: Objective, value: Double) { objectives[objective] = value }
    override fun getObjective(objective: Objective): Double = objectives[objective] as Double
}

/** Internal iteration implementation. */
internal class InternalIteration(override val index: Int, override val stopped: Boolean,
                                 override val candidates: List<EvaluatedCandidate>): Iteration

