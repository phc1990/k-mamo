package org.phc1990.mammok.problems.singleobjective.convex

import org.phc1990.mammok.optimization.Candidate
import org.phc1990.mammok.optimization.Iteration
import org.phc1990.mammok.optimization.OptimizationCriterion
import org.phc1990.mammok.problems.OptimizationTestProblem
import org.phc1990.mammok.topology.space.implementation.DoubleInterval
import org.phc1990.mammok.topology.space.implementation.IntegerInterval
import kotlin.math.pow

/**
 * Sphere function.
 *
 * Variables:
 * - {x1, x2, ..., xn} with 1 < n < +Inf
 *
 * Objectives:
 * - f1 = x1 * x1 + x2 * x2 + ... + xn * xn
 *
 * Optimum 1:
 * - x1 = x2 = ... = xn = 0, f1 = 0
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 6, 2020
 */
class SphereFunction(private val dimensions: Int, private val realDimensions: Int,
                     semiInterval: Double, private val realTolerance: Double): OptimizationTestProblem() {

    private val realInterval: DoubleInterval = DoubleInterval(-semiInterval, semiInterval, realTolerance)
    private val integerInterval: IntegerInterval = IntegerInterval(-semiInterval.toInt(), semiInterval.toInt(), 1)

    init {
        variables = Array(dimensions) { i ->
            if (i < realDimensions) { realInterval } else { integerInterval }
        }

        objectives = arrayOf(OptimizationCriterion.MINIMIZE)
        name = "Sphere Function, dimensions: $dimensions, " +
                "real dimensions: $realDimensions, integer dim: ${dimensions-realDimensions}, " +
                "search interval: [-$semiInterval, +$semiInterval], tolerance: $realTolerance"
    }

    override fun evaluate(candidate: Candidate) {
        var f = 0.0
        for (i in 0 until dimensions) {
            f += if (i < realDimensions) {
                candidate.getVariable(i, Double::class.java).pow(2.0)
            } else {
                candidate.getVariable(i, Int::class.java).toDouble().pow(2.0)
            }
        }
        candidate.objectives[0] = f
    }

    override fun validate(iteration: Iteration) {
        val candidate = iteration.candidates[0]
        for (i in 0 until dimensions) {
            if (i < realDimensions) {
                validateVariable(realInterval, candidate.getVariable(i, Double::class.java), 0.0, realTolerance)
            } else {
                validateVariable(integerInterval, candidate.getVariable(i, Int::class.java), 0, 0.0)
            }
        }
    }
}
