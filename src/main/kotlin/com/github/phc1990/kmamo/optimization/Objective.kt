package com.github.phc1990.kmamo.optimization

/**
 * An optimization variable.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 31, 2020
 */
interface Objective {

    /** Objective's name. */
    val name: String

    /** Objective's optimization criterion. */
    val criterion: OptimizationCriterion

}

/**
 * Optimization criterion.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 31, 2020
 */
enum class OptimizationCriterion {

    /** Minimization criterion. */
    MINIMIZE,

    /** Maximization criterion. */
    MAXIMIZE

}

/**
 * A [Objective] factory.
 *
 * @see Objective
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 31, 2020
 */
internal abstract class ObjectiveFactory {

    companion object {

        /** Returns a new instance of [Objective]. */
        fun get(name: String, criterion: OptimizationCriterion): Objective = DefaultObjective(name, criterion)

        /** Default implementation of [Objective], used by the [ObjectiveFactory]. */
        private class DefaultObjective(override val name: String,
                                       override val criterion: OptimizationCriterion): Objective
    }
}
