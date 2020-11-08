package org.phc1990.mammok.algorithm.hillclimbing

import org.junit.Test
import org.phc1990.mammok.problems.singleobjective.convex.SphereFunction

class SimpleHillClimbingTest {

    @Test
    fun sphereFunction() {

        val problem = SphereFunction(3, 2, 10.0, 0.01)
        val algorithm = SimpleHillClimbing()
        problem.solve(algorithm)
    }

}