package com.github.phc1990.kmamo.algorithm

import com.github.phc1990.kmamo.optimization.Objective
import com.github.phc1990.kmamo.optimization.Variable
import com.github.phc1990.kmamo.space.Space

/**
 * An algorithm.
 *
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface Algorithm {

    /** Solves the problem using the given black-box [evaluator] and iteration [processor]. */
    fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor)

    /** Solves the problem using the given black-box [evaluator] function and the iteration [processor] function. */
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

/**
 * A candidate solution. This interface contains no information on the evaluation status of the instance.
 *
 * @see UnevaluatedCandidate
 * @see EvaluatedCandidate
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface Candidate {

    /** The index of this instance amongst the other instances within the same iteration. */
    val index: Int

    /** The algorithm iteration to which this instance belongs. */
    val iterationIndex: Int

    /** Returns the value assigned to the variable. */
    fun <T> getVariable(variable: Variable<T>): T
}

/**
 * A candidate that allows objective evaluation.
 *
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface UnevaluatedCandidate: Candidate {

    /** Sets the value of the given objective. */
    fun setObjective(objective: Objective, value: Double)
}

/**
 * A candidate has been evaluated.
 *
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface EvaluatedCandidate: Candidate {

    /** Returns the value of the given objective. */
    fun getObjective(objective: Objective): Double
}

/**
 * An algorithm iteration. It contains information on:
 *
 * - its index (iteration number)
 * - its stopping criterion
 * - its candidate solutions
 *
 * Additionally, one can overwrite the stop criterion externally.
 *
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface Iteration {

    /** The index of the instance. */
    val index: Int

    /** List of evaluated candidates that constitute this iteration. */
    val candidates: List<EvaluatedCandidate>

    /** Side of the evaluated candidates. */
    fun size(): Int = candidates.size

    /** Returns whether the stop criterion has been met. */
    fun isStop(): Boolean

    /** Forces the stop criterion on the instance. Returns true if the stop criterion was actually overwritten, false
     * if the stop criterion had already been met before calling this function. */
    fun forceStop(): Boolean
}

/**
 * A Black-box candidate evaluator. The purpose of these instances is:
 *
 * 1. Receive an [UnevaluatedCandidate] solution
 * 2. Evaluate the objective functions (´black-box´ evaluation)
 * 3. Set the objectives values to the candidate solution
 *
 * @see UnevaluatedCandidate
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface BlackBoxEvaluator {
    fun evaluate(candidate: UnevaluatedCandidate)
}

/**
 * An iteration processor. The purpose of these instances is:
 *
 * 1. Receive the latest [Iteration]
 * 2. Analyse the iteration contents and force the stop criterion if needed
 * 3. Handle the iteration (e.g. print it, dump it to a file, plot it, etc.)
 *
 * @see Iteration
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface IterationProcessor {
    fun process(iteration: Iteration)
}

/**
 * Internal [Candidate] implementation.
 *
 * @see Candidate
 * @see UnevaluatedCandidate
 * @see EvaluatedCandidate
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
internal class InternalCandidate(override val index: Int, override val iterationIndex: Int,
                                 private val variables: Map<Any, Any>): UnevaluatedCandidate, EvaluatedCandidate {

    internal val objectives: MutableMap<Objective, Double> = mutableMapOf()
    override fun <T> getVariable(variable: Variable<T>): T = variables[variable] as T
    override fun setObjective(objective: Objective, value: Double) { objectives[objective] = value }
    override fun getObjective(objective: Objective): Double = objectives[objective]!!
}

/**
 * Internal [Iteration] implementation.
 *
 * @see Iteration
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
internal class InternalIteration(override val index: Int, override val candidates: List<EvaluatedCandidate>,
                                 private var stop: Boolean): Iteration {

    override fun isStop(): Boolean = stop

    override fun forceStop(): Boolean {

        if (stop) {
            return false
        }

        stop = true
        return true
    }
}

