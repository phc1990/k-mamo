package org.phc1990.mammok.problems

import org.phc1990.mammok.api.*
import org.phc1990.mammok.topology.space.MetricSpace
import org.phc1990.mammok.topology.space.Space
import org.phc1990.mammok.utils.CandidateComparators
import org.phc1990.mammok.utils.IterationProcessors
import org.phc1990.mammok.utils.OptimalSetPruners
import java.lang.Appendable
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Optimization test problem.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 6, 2020
 */
abstract class OptimizationTestProblem(
        private val comparator: CandidateComparator,
        private val pruner: OptimalSetPruner): CandidateEvaluator {

    protected var numberOfEvaluations: Int = 0
    protected lateinit var name: String
    protected lateinit var variables: Array<*>

    @Synchronized
    fun <S: Space<Any>> solve(algorithm: Algorithm<S>) {
        numberOfEvaluations = 0
        variables.forEach { algorithm.addVariable(it as S) }
        algorithm.run(this, comparator, pruner, getProcessor(algorithm.name))
    }

    /** Validates the final result. */
    protected abstract fun validate(iteration: Iteration)

    /** Returns true if the variable is within the margin distance of the expected value. */
    protected fun <T> validateVariable( index: Int, space: MetricSpace<T>, expected: T, actual: T,
                                        margin: Double) {
        assertTrue(abs(space.metric(expected, actual)) <= margin,
                "Variable $index, expected: $expected, actual: $actual")
    }

    private fun getProcessor(algorithmName: String, appendable: Appendable = System.out): IterationProcessor = object : IterationProcessor {
        private  val startTime = System.currentTimeMillis()

        init {
            appendable.appendln("********")
            appendable.appendln("Test problem           : $name")
            appendable.appendln("Algorithm              : $algorithmName")
            appendable.appendln("Candidate comparator   : ${comparator.name}")
            appendable.appendln("Optimal set pruner     : ${pruner.name}")
        }

        override fun process(iteration: Iteration): Boolean {
            if (iteration.stop) {
                appendable.appendln("Number of evaluations  : $numberOfEvaluations")
                validate(iteration)
                appendable.appendln("Validation             : OK")
                appendable.appendln("Computational time [s] : ${1E-3 * (System.currentTimeMillis()-startTime)}")
            }
            return false
        }

    }
}