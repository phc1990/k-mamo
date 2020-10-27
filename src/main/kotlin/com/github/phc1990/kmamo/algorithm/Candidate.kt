package com.github.phc1990.kmamo.algorithm

import com.github.phc1990.kmamo.optimization.Objective
import com.github.phc1990.kmamo.optimization.OptimizationCriterion
import com.github.phc1990.kmamo.optimization.Variable
import com.github.phc1990.kmamo.space.Space

/**
 * A candidate solution.
 *
 * @author Pau Hebrero Casasayas - Jun 1, 2020
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
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
internal class InternalCandidate(override val iterationIndex: Int, override val candidateIndex: Int,
                                 private val variables: Map<Variable<*>, Any>): Candidate {

    internal val objectives: MutableMap<Objective, Double> = mutableMapOf()
    override fun <T> getVariable(variable: Variable<T>): T = variables[variable] as T
    override fun setObjective(objective: Objective, value: Double) { objectives[objective] = value }
    override fun getObjective(objective: Objective): Double = objectives[objective]!!

    companion object {

        /** Returns a new uniformly distributed generated instance. */
        fun uniform(iterationIndex: Int, candidateIndex: Int, variables: Map<Variable<*>, Any>): InternalCandidate =
                InternalCandidate(iterationIndex, candidateIndex,
                        variables.entries.associate { e -> Pair(e.key, (e.value as Space<Any>).uniform())})
    }
}
