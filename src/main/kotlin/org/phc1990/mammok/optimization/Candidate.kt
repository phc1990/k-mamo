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

    /** Map containing the variable and its value. */
    val variables: Map<Variable<*>, Any>

    /** Returns the value assigned to the variable. */
    fun <T> getVariable(variable: Variable<T>): T = variables[variable] as T

    /** Sets the value of the given objective. */
    fun setObjective(objective: Objective, value: Double)

    /** Returns the value of the given objective. */
    fun getObjective(objective: Objective): Double

    /** Returns true if the instance' objective value is better than the other' objective value. */
    fun challenge(other: Candidate, objective: Objective): Boolean =
            if (objective.criterion == OptimizationCriterion.MAXIMIZE)
                getObjective(objective) > other.getObjective(objective)
            else getObjective(objective) < other.getObjective(objective)

    /**
     * Returns the improvement of the instance' objective value w.r.t. the other' objective value (the returned number
     * will be positive if the instance' objective is better than the other' objective, regardless of the optimisation
     * criterion).
     */
    fun improvement(other: Candidate, objective: Objective): Double =
            if (objective.criterion == OptimizationCriterion.MAXIMIZE)
                getObjective(objective) - other.getObjective(objective)
            else -getObjective(objective) + other.getObjective(objective)
}

/**
 * Internal [Candidate] implementation.
 *
 * @see Candidate
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
internal class InternalCandidate(override val iterationIndex: Int, override val candidateIndex: Int,
                                 override val variables: Map<Variable<*>, Any>): Candidate {

    internal val objectives: MutableMap<Objective, Double> = mutableMapOf()
    override fun setObjective(objective: Objective, value: Double) { objectives[objective] = value }
    override fun getObjective(objective: Objective): Double = objectives[objective]!!

    companion object {

        /** Returns a new uniformly distributed generated instance. */
        fun uniform(iterationIndex: Int, candidateIndex: Int, variables: Array<Variable<*>>): InternalCandidate =
                InternalCandidate(iterationIndex, candidateIndex,
                        variables.toList().associateWith { v -> (v.space as Space<Any>).uniform() })
    }
}
