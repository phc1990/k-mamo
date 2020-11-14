package org.phc1990.mammok.optimization

import org.phc1990.mammok.api.Candidate
import org.phc1990.mammok.topology.space.Space

/**
 * Internal [Candidate] implementation.
 *
 * @see Candidate
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
internal class InternalCandidate(private val variables: Array<*>): Candidate {

    override var objectives: DoubleArray = DoubleArray(0)
    override fun variablesSize(): Int = variables.size
    override fun <T> getVariable(index: Int, type: Class<T>): T = variables[index] as T

    fun copy(): InternalCandidate = InternalCandidate(variables)

    companion object {

        /** Returns a new uniformly distributed generated instance. */
        fun uniform(variableSpaces: Array<Space<Any>>): InternalCandidate =
                InternalCandidate(Array(variableSpaces.size){ index -> variableSpaces[index].uniform()})
    }
}
