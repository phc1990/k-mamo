package org.phc1990.mammok.problems.singleobjective.convex

import org.phc1990.mammok.api.Candidate
import org.phc1990.mammok.api.CandidateComparator
import org.phc1990.mammok.api.Iteration
import org.phc1990.mammok.api.OptimalSetPruner
import org.phc1990.mammok.problems.OptimizationTestProblem
import org.phc1990.mammok.topology.space.search.RealInterval
import org.phc1990.mammok.topology.space.search.IntegerInterval
import org.phc1990.mammok.utils.CandidateComparators
import org.phc1990.mammok.utils.OptimalSetPruners
import kotlin.math.pow
import kotlin.test.assertEquals

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
                     semiInterval: Double, private val realTolerance: Double):
        OptimizationTestProblem(CandidateComparators.getWeightedSum(1.0), OptimalSetPruners.getNone()) {

    private val realInterval: RealInterval = RealInterval(-semiInterval, semiInterval, realTolerance)
    private val integerInterval: IntegerInterval = IntegerInterval(-semiInterval.toInt(), semiInterval.toInt(), 1)

    init {
        variables = Array(dimensions) { i ->
            if (i < realDimensions) { realInterval } else { integerInterval }
        }

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
        candidate.objectives += f
        numberOfEvaluations++
    }

    override fun validate(iteration: Iteration) {
        assertEquals(iteration.optimalSet.size, 1)
        iteration.optimalSet.forEach {
            for (i in 0 until dimensions) {
                if (i < realDimensions) {
                    validateVariable(i, realInterval, 0.0, it.getVariable(i, Double::class.java), realTolerance)
                } else {
                    validateVariable(i, integerInterval, 0, it.getVariable(i, Int::class.java), 0.0)
                }
            }
        }
    }
}
