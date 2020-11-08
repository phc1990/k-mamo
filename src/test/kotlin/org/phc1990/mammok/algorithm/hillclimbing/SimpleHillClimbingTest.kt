package org.phc1990.mammok.algorithm.hillclimbing

import org.junit.Test
import org.phc1990.mammok.problems.singleobjective.convex.SphereFunction
import org.phc1990.mammok.optimization.ObjectiveFactory
import org.phc1990.mammok.optimization.OptimizationCriterion
import org.phc1990.mammok.optimization.Variable
import org.phc1990.mammok.topology.space.implementation.DoubleInterval

class SimpleHillClimbingTest {

    @Test
    fun sphereFunction() {

        val tolerance = 0.01
        val space = DoubleInterval(-1.0, -1.0, tolerance, false)
        val objective = ObjectiveFactory.get("obj-1", OptimizationCriterion.MINIMIZE)

        val algorithm = SimpleHillClimbing(objective)
        var variables : Array<Variable<Double>> = arrayOf()
        for (i in 0 until 3) {
            variables += algorithm.addVariable("var-$i", space)
        }

        val function = SphereFunction(objective, variables)

    }

}