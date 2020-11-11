package org.phc1990.mammok.problems

import org.phc1990.mammok.api.*
import org.phc1990.mammok.topology.space.MetricSpace
import org.phc1990.mammok.topology.space.Space
import org.phc1990.mammok.utils.CandidateComparators
import org.phc1990.mammok.utils.IterationProcessors
import org.phc1990.mammok.utils.OptimalSetPruners
import java.lang.Appendable
import kotlin.math.abs

/**
 * Optimization test problem.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 6, 2020
 */
abstract class OptimizationTestProblem(
        private val name: String,
        private val comparator: CandidateComparator,
        private val pruner: OptimalSetPruner = OptimalSetPruners.getNone()): CandidateEvaluator {

    protected lateinit var variables: Array<*>

    @Synchronized
    fun <S: Space<Any>> solve(algorithm: Algorithm<S>) {
        variables.forEach { algorithm.addVariable(it as S) }
        algorithm.run(this, comparator, pruner, IterationProcessors.getLogger())
    }

    /** Validates the final result. */
    protected abstract fun validate(iteration: Iteration)

    /** Returns true if the variable is within the margin distance of the expected value. */
    protected fun <T> validateVariable( space: MetricSpace<T>, actual: T, expected: T,
                                        margin: Double): Boolean = abs(space.metric(actual, expected)) <= margin

    private fun getProcessor(appendable: Appendable = System.out): IterationProcessor = object : IterationProcessor {
        private  val startTime = System.currentTimeMillis()

        override fun process(iteration: Iteration): Boolean {
            if (iteration.stop) {
                appendable.appendln("********")
                appendable.appendln("Test problem   : $name")
                appendable.appendln("Comparator     : ${comparator.name}")
                appendable.appendln("Pruner         : ${pruner.name}")
                appendable.appendln("Comp. Time [s] : ${1E-3 * (System.currentTimeMillis()-startTime)}")
                validate(iteration)
                appendable.appendln("Validation     : OK")
            }
            return false
        }

    }
}