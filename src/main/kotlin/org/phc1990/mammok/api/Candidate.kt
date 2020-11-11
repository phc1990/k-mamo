package org.phc1990.mammok.api

/**
 * A candidate solution.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface Candidate {

    /** The algorithm iteration to which this instance belongs. */
    val iterationIndex: Int

    fun variablesSize(): Int

    /** Returns the variable value. */
    fun <T> getVariable(index: Int, type: Class<T>): T

    /** Sets the objective value. */
    var objectives: DoubleArray
}