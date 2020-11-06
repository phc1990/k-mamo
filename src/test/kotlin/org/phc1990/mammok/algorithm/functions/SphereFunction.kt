package org.phc1990.mammok.algorithm.functions

import org.phc1990.mammok.optimization.*
import kotlin.math.pow
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SphereFunction(private val objective: Objective, private val variables: Array<Variable<Double>>):
        TestFunction("Sphere Function") {

    override fun evaluate(candidate: Candidate) {
        var f = 0.0
        variables.forEach { v -> f += candidate.getVariable(v).pow(2.0) }
        candidate.setObjective(objective, f)
    }

    override fun validate(iteration: Iteration) {
        variables.forEach {v ->
            assertTrue(Math.abs(v) <= v.space.)
        }
    }
}