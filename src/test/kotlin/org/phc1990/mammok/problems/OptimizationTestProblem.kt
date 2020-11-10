package org.phc1990.mammok.problems

import org.phc1990.mammok.algorithm.Algorithm
import org.phc1990.mammok.optimization.*
import org.phc1990.mammok.topology.space.MetricSpace
import org.phc1990.mammok.topology.space.Space
import kotlin.math.abs

/**
 * Optimization test problem.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 6, 2020
 */
abstract class OptimizationTestProblem {

    lateinit var name: String
    protected lateinit var variables: Array<*>
    private var startTime: Long = 0
    private lateinit var algorithmName: String

    @Synchronized
    fun <S: Space<Any>> solve(algorithm: Algorithm<S>,
                              prevalence: (c1: Candidate, c2: Candidate) -> Int,
                              pruning: (set: Set<Candidate>) -> Unit) {
        variables.forEach { algorithm.addVariable(it as S) }
        algorithmName = algorithm.name
        startTime = System.currentTimeMillis()
        algorithm.run(::evaluate, prevalence, pruning, ::process)
    }

    /** Processes the iteration. */
    private fun process(iteration: Iteration): Boolean {
        if (iteration.stop) {
            printSummary(1E-3 * (System.currentTimeMillis() - startTime), iteration)
            validate(iteration)
        }
        return false
    }

    /** Evaluates the candidate. */
    protected abstract fun evaluate(candidate: Candidate): Unit

    /** Validates the final result. */
    private fun validate(iteration: Iteration) = iteration.optimalSet.forEach { validateCandidate(it) }

    /** Validates a candidate. */
    protected abstract fun validateCandidate(candidate: Candidate)

    /** Returns true if the variable is within the margin distance of the expected value. */
    protected fun <T> validateVariable( space: MetricSpace<T>, actual: T, expected: T,
                                        margin: Double): Boolean = abs(space.metric(actual, expected)) <= margin

    private fun printSummary(processingTime: Double, iteration: Iteration) {
        println("Algorithm: $algorithmName, Problem: $name, Comp. Time: $processingTime [s]")
        println("Iteration ${iteration.index}")
        for (optimal in iteration.optimalSet) {
            println("Optimal, iteration: ${optimal.iterationIndex}")
            for (i in variables.indices) {
                println("Variable $i: ${optimal.getVariable(i, Any::class.java)}")
            }
            println("")
            for (i in optimal.objectives.indices) {
                println("Objective $i: ${optimal.objectives[i]}")
            }
            println("----")
        }
    }
}