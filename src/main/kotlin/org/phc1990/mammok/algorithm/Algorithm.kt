package org.phc1990.mammok.algorithm

import org.phc1990.mammok.optimization.Candidate
import org.phc1990.mammok.optimization.Iteration
import org.phc1990.mammok.topology.space.LinearSpace
import org.phc1990.mammok.topology.space.Space

/**
 * An algorithm.
 *
 * @param S the variable space type allowed (e.g. [LinearSpace])
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface Algorithm<S: Space<Any>> {

    /** Name of the algorithm. */
    val name: String

    /** Adds a new optimization variable, returning its reference index. */
    fun addVariable(space: S): Int

    /** Runs the algorithm using the given black-box [evaluate] function, the [prevalence] comparison function and the
     * iteration [process] function. */
    fun run(evaluate: (candidate: Candidate) -> Unit,
            prevalence: (o1: Candidate, o2: Candidate) -> Int,
            process: (iteration: Iteration) -> Boolean)
}

