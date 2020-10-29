package com.github.phc1990.kmamo.optimization

import com.github.phc1990.kmamo.topology.Space

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

    /** Returns the value assigned to the variable. */
    fun <T> getVariable(variable: Variable<T>): T

    /** Sets the value of the given objective. */
    fun setObjective(objective: Objective, value: Double)

    /** Returns the value of the given objective. */
    fun getObjective(objective: Objective): Double

    /** Returns true if the instance' objective value is better than the other' objective value. */
    fun challenge(other: Candidate, objective: Objective): Boolean =
            if (objective.criterion == OptimizationCriterion.MAXIMIZE)
                getObjective(objective) > other.getObjective(objective)
            else getObjective(objective) < other.getObjective(objective)
}

/**
 * Internal [Candidate] implementation.
 *
 * @see Candidate
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
internal class InternalCandidate(override val iterationIndex: Int, override val candidateIndex: Int,
                                 internal val variables: Map<Variable<*>, Any>): Candidate {

    internal val objectives: MutableMap<Objective, Double> = mutableMapOf()
    override fun <T> getVariable(variable: Variable<T>): T = variables[variable] as T
    override fun setObjective(objective: Objective, value: Double) { objectives[objective] = value }
    override fun getObjective(objective: Objective): Double = objectives[objective]!!

    companion object {

        /** Returns a new uniformly distributed generated instance. */
        fun uniform(iterationIndex: Int, candidateIndex: Int, variables: List<Variable<*>>): InternalCandidate =
                InternalCandidate(iterationIndex, candidateIndex,
                        variables.associateWith { v -> (v.space as Space<Any>).uniform() })
    }
}
