package org.phc1990.mammok.problems

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.phc1990.mammok.algorithm.AbstractAlgorithm
import org.phc1990.mammok.algorithm.hillclimbing.HillClimbing
import org.phc1990.mammok.algorithm.hillclimbing.StochasticHillClimbing
import org.phc1990.mammok.problems.singleobjective.convex.SphereFunction
import org.phc1990.mammok.problems.singleobjective.nonconvex.RastriginFunction
import org.phc1990.mammok.random.Random

@RunWith(Parameterized::class)
class ProblemTestList(private val problem: OptimizationTestProblem,
                      private val algorithms: Array<AbstractAlgorithm<*>>) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(

                    // Sphere Function, 3 real dimensions
                    arrayOf(SphereFunction(3, 3, 10.0, 0.001),
                            arrayOf(
                                    HillClimbing(false),
                                    HillClimbing(true ),
                                    StochasticHillClimbing(false, 100),
                                    StochasticHillClimbing(true, 100)
                            )),

                    // Sphere Function, 3 integer dimensions
                    arrayOf(SphereFunction(3, 0, 10000.0, 0.001),
                            arrayOf(
                                    HillClimbing(false),
                                    HillClimbing(true),
                                    StochasticHillClimbing(false, 100),
                                    StochasticHillClimbing(true, 100)
                            )),

                    // Sphere Function, 2 real, 1 integer dimensions
                    arrayOf(SphereFunction(3, 2, 10.0, 0.001),
                            arrayOf(
                                    HillClimbing(false),
                                    HillClimbing(true),
                                    StochasticHillClimbing(false, 100),
                                    StochasticHillClimbing(true, 100)
                            )),
                    // Rastrigin Function, 2 dimensions
                    arrayOf(RastriginFunction(2, 0.001),
                            arrayOf(
                                    StochasticHillClimbing(false,100000),
                                    StochasticHillClimbing(true,100000)
                            )),
                    // Rastrigin Function, 3 dimensions
                    arrayOf(RastriginFunction(3, 0.001),
                            arrayOf(
                                    StochasticHillClimbing(false, 1E7.toInt()),
                                    StochasticHillClimbing(true, 1E7.toInt())
                            ))
            )
        }
    }

    @Test
    fun test() {
        algorithms.forEach { algorithm ->
            // Set seed for traceability
            Random.setSeed(0)
            // Solve the problem
            problem.solve(algorithm)
        }
    }

}