package com.github.phc1990.kmamo.optimization

/**
 * An optimization variable.
 *
 * @author Pau Hebrero Casasayas - May 31, 2020
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
 * @author Pau Hebrero Casasayas - May 31, 2020
 */
enum class OptimizationCriterion {

    /** Minimization criterion. */
    MINIMIZE,

    /** Maximization criterion. */
    MAXIMIZE

}