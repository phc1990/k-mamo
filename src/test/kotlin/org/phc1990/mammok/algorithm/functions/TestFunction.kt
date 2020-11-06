package org.phc1990.mammok.algorithm.functions

import org.phc1990.mammok.optimization.BlackBoxEvaluator
import org.phc1990.mammok.optimization.Iteration
import org.phc1990.mammok.optimization.IterationProcessor

/**
 * An optimisation testing function.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 6, 2020
 */
abstract class TestFunction(val name: String): IterationProcessor, BlackBoxEvaluator {

    private var start: Long = 0

    override fun initialize() {
        start = System.currentTimeMillis()
    }

    override fun process(iteration: Iteration): Boolean {
        if (iteration.stop) {
            val solvingTime = 1E-3 * (System.currentTimeMillis() - start)
            println("$name solving time [s]: $solvingTime")
            validate(iteration)
        }
        return false
    }

    /** Validates the final result. */
    abstract fun validate(iteration: Iteration)
}