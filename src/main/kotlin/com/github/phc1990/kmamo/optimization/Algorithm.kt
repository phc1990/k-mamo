package com.github.phc1990.kmamo.optimization

/**
 * An algorithm.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface Algorithm {

    /** Name of the algorithm. */
    val name: String

    /** Solves the problem using the given black-box [evaluator] and iteration [processor]. */
    fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor)

    /** Solves the problem using the given black-box [evaluator] function and the iteration [processor] function. */
    fun solve(evaluator: (candidate: Candidate) -> Unit,
              processor: (iteration: Iteration) -> Boolean) {

        val anonymousEvaluator = object : BlackBoxEvaluator {
            override fun evaluate(candidate: Candidate) = evaluator.invoke(candidate)
        }

        val anonymousProcessor = object : IterationProcessor {
            override fun process(iteration: Iteration): Boolean = processor.invoke(iteration)
        }

        solve(anonymousEvaluator, anonymousProcessor)
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
