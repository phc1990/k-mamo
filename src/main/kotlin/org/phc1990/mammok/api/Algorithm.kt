package org.phc1990.mammok.api

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

    fun run(evaluator: CandidateEvaluator,
            comparator: CandidateComparator,
            pruner: OptimalSetPruner,
            processor: IterationProcessor)
}

