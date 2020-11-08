package org.phc1990.mammok.optimization

import org.phc1990.mammok.topology.space.Space

/**
 * A candidate solution.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface Candidate {

    /** The algorithm iteration to which this instance belongs. */
    val iterationIndex: Int

    /** The index of this instance amongst the other instances within the same iteration. */
    val candidateIndex: Int

    /** Returns the variable value. */
    fun <T> getVariable(index: Int, type: Class<T>): T

    /** Sets the objective value. */
    fun setObjective(index: Int, value: Double)

    /** Returns the objective value. */
    fun getObjective(index: Int): Double
}

/**
 * Internal [Candidate] implementation.
 *
 * @see Candidate
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
internal class InternalCandidate(override val iterationIndex: Int, override val candidateIndex: Int,
                                 internal val variables: Array<*>, numberOfObjectives: Int,
                                 internal val objectives: DoubleArray = DoubleArray(numberOfObjectives)): Candidate {

    companion object {

        /** Returns a new uniformly distributed generated instance. */
        fun uniform(iterationIndex: Int, candidateIndex: Int, variableSpaces: Array<Space<*>>,
                    numberOfObjectives: Int): InternalCandidate {

            return InternalCandidate(   iterationIndex,
                                        candidateIndex,
                                        Array(variableSpaces.size){ index -> variableSpaces[index].uniform()},
                                        numberOfObjectives)
        }
    }

    override fun <T> getVariable(index: Int, type: Class<T>): T = variables[index] as T
    override fun setObjective(index: Int, value: Double) { objectives[index] = value }
    override fun getObjective(index: Int): Double = objectives[index]

    /** Returns true if the instance objective value is better than the other objective value. */
    fun challenge(other: Candidate, index: Int, criterion: OptimizationCriterion): Boolean =
            if (criterion == OptimizationCriterion.MAXIMIZE)
                getObjective(index) > other.getObjective(index)
            else getObjective(index) < other.getObjective(index)

    /**
     * Returns the improvement of the instance' objective value w.r.t. the other' objective value (the returned number
     * will be positive if the instance' objective is better than the other' objective, regardless of the optimisation
     * criterion).
     */
    fun improvement(other: Candidate, index: Int, criterion: OptimizationCriterion): Double =
            if (criterion == OptimizationCriterion.MAXIMIZE)
                getObjective(index) - other.getObjective(index)
            else -getObjective(index) + other.getObjective(index)
}
