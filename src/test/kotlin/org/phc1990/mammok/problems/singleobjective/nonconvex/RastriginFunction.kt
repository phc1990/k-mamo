package org.phc1990.mammok.problems.singleobjective.nonconvex

import org.phc1990.mammok.api.Candidate
import org.phc1990.mammok.api.Iteration
import org.phc1990.mammok.problems.OptimizationTestProblem
import org.phc1990.mammok.topology.space.search.RealInterval
import org.phc1990.mammok.utils.CandidateComparators
import org.phc1990.mammok.utils.OptimalSetPruners
import kotlin.math.cos
import kotlin.math.pow
import kotlin.test.assertEquals

/**
 * Rastrigin function.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 9, 2020
 */
class RastriginFunction(private val dimensions: Int, private val tolerance: Double):
        OptimizationTestProblem(CandidateComparators.getWeightedSum(1.0), OptimalSetPruners.getNone()) {

    private val interval = RealInterval(-5.12, 5.12, tolerance, false)

    init {
        variables = Array(dimensions){interval}
        name = "Rastrigin Function, dimensions: $dimensions, tolerance: $tolerance"
    }

    override fun evaluate(candidate: Candidate) {
        var f = 10.0 * dimensions
        for (i in variables.indices) {
            val x = candidate.getVariable(i, Double::class.java)
            f += x.pow(2.0) - 10.0 * cos(2.0 * Math.PI * x)
        }
        candidate.objectives += f
        numberOfEvaluations++
    }

    override fun validate(iteration: Iteration) {
        assertEquals(1, iteration.optimalSet.size)
        iteration.optimalSet.forEach {
            for (i in 0 until it.variablesSize()) {
                validateVariable(i, interval, 0.0, it.getVariable(i, Double::class.java), tolerance)
            }
        }
    }

}