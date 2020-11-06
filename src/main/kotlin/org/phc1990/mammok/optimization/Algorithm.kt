package org.phc1990.mammok.optimization

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

    /** Solves the problem using the given black-box [evaluate] function and the iteration [process] function. */
    fun solve(evaluate: (candidate: Candidate) -> Unit,
              initialise: () -> Unit,
              process: (iteration: Iteration) -> Boolean) {

        val anonymousEvaluator = object : BlackBoxEvaluator {
            override fun evaluate(candidate: Candidate) = evaluate.invoke(candidate)
        }

        val anonymousProcessor = object : IterationProcessor {
            override fun initialize() = initialise.invoke()
            override fun process(iteration: Iteration): Boolean = process.invoke(iteration)
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

    /** This function will be called right before starting to solve the problem (it can be used for instance, to
     * record the current time). */
    fun initialize()

    /** Processes the given [iteration], returning true if the process is to be stopped. */
    fun process(iteration: Iteration): Boolean
}
