package org.phc1990.mammok.optimization

import org.phc1990.mammok.topology.space.LinearSpace
import org.phc1990.mammok.topology.space.Space

/**
 * An algorithm.
 *
 * @param S the variable space type allowed (e.g. [LinearSpace])
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface Algorithm<S: Space<*>> {

    /** Name of the algorithm. */
    val name: String

    /** Adds a new optimization variable, returning its reference index. */
    fun addVariable(space: S): Int

    /** Adds a new optimization objective, returning its reference index. */
    fun addObjective(criterion: OptimizationCriterion): Int

    /** Runs the algorithm using the given black-box [evaluator] and iteration [processor]. */
    fun run(evaluator: BlackBoxEvaluator, processor: IterationProcessor)

    /** Runs the algorithm using the given black-box [evaluate] function and the iteration [process] function. */
    fun run(evaluate: (candidate: Candidate) -> Unit,
            process: (iteration: Iteration) -> Boolean) {

        val anonymousEvaluator = object : BlackBoxEvaluator {
            override fun evaluate(candidate: Candidate) = evaluate.invoke(candidate)
        }

        val anonymousProcessor = object : IterationProcessor {
            override fun process(iteration: Iteration): Boolean = process.invoke(iteration)
        }

        run(anonymousEvaluator, anonymousProcessor)
    }
}

/**
 * A Black-box candidate evaluator. The purpose of these instances is:
 *
 * 1. Receive an cancidate solution
 * 2. Evaluate the objective functions (´black-box´ evaluation)
 * 3. Set the objectives values to the candidate solution
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface BlackBoxEvaluator {

    /** Evaluates the given [candidate]. */
    fun evaluate(candidate: Candidate)
}

/**
 * An iteration processor. The purpose of these instances is:
 *
 * 1. Receive the latest [Iteration]
 * 2. Handle the iteration (e.g. print it, dump it to a file, plot it, etc.)
 *
 * @see Iteration
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface IterationProcessor {

    /** Processes the given [iteration], returning true if the process is to be stopped. */
    fun process(iteration: Iteration): Boolean
}
