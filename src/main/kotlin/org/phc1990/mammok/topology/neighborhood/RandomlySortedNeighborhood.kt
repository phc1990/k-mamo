package org.phc1990.mammok.topology.neighborhood

import org.phc1990.mammok.optimization.Candidate
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.optimization.Variable
import org.phc1990.mammok.utils.random.Random
import org.phc1990.mammok.topology.space.Space
import java.lang.RuntimeException

/**
 * A [Neighborhood] that provides randomly sorted neighbors.
 *
 * @see Neighborhood
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990)- Oct 31, 2020
 */
internal class RandomlySortedNeighborhood: Neighborhood {

    private var iterationIndex: Int
    private var nextCandidateIndex: Int = 0
    private var variables = arrayOf<Variable<*>>()

    /** A matrix containing each of the neighbor values for each variable. This contains all the potential values for
     * each variable, that, in combination, can spawn any neighbor. (e.g. {{-1,0,+1}, {"a","b"}}). For each variable,
     * the first value [0] is that of the point. */
    private var values = arrayOf<Array<Any>>()

    /** An array containing the amount of potential values for each one of the variables (e.g. {3,2}). */
    private var sizes = arrayOf<Int>()

    /** An iterator that provides the next neighbor ordinal. */
    private val ordinalIterator: Iterator<Int>

    constructor(iterationIndex: Int, variables: Map<Variable<*>, Any>) {

        this.iterationIndex = iterationIndex

        variables.entries.forEach { e ->
            var array = arrayOf<Any>()

            // Add the point itself (index 0 of the array)
            array += e.value

            // Add the neighbors to the point (index 1,2,...)
            (e.key.space as Space<Any>).neighbors(e.value)?.forEach { n -> array += n }

            sizes += array.size
            values += array
            this.variables += e.key
        }

        if (values.size != variables.size || sizes.size != variables.size)
            throw RuntimeException("Neighborhood dimensions does not match variable space dimension")

        var totalNeighbors = 1
        for (size in sizes) {
            totalNeighbors *= size
        }

        // Exclude ordinal [0] as it would translate into the point itself
        ordinalIterator = Random.nonRepeatingUniformInteger(1, totalNeighbors)
    }

    override fun hasNext(): Boolean = ordinalIterator.hasNext()
    override fun next(): Candidate = candidate(indexes(ordinalIterator.next()))

    /**
     * Translates an array of indexes into a candidate. Each index indicates which element is to be picked for each
     * one of the variables. For instance, {2,0} will select the third ([2]) value for the first variable and the first
     * value ([0]) for the second variable.
     */
    private fun candidate(indexes: Array<Int>): Candidate {

        val map = mutableMapOf<Variable<*>, Any>()

        for (i in variables.indices) {
            map[variables[i]] = values[i][indexes[i]]
        }

        val candidate = InternalCandidate(iterationIndex, nextCandidateIndex, map)
        nextCandidateIndex++

        return candidate
    }

    /**
     * Translates the ordinal of a neighbor into its corresponding indexes for each one of the variables.
     *
     * For instance, in the case of 3 variables, where:
     * - variable '1' can have up to 2 values
     * - variable '2' can have up to 3 values
     * - variable '3' can have up to 2 values
     *
     * Then:
     * - the ordinal '0' would correspond to indexes {0,0,0}
     * - the ordinal '1' would correspond to indexes {0,0,1}
     * - the ordinal '2' would correspond to indexes {0,1,0}
     * - the ordinal '3' would correspond to indexes {0,1,1}
     * - the ordinal '4' would correspond to indexes {0,2,1}
     * - the ordinal '5' would correspond to indexes {0,3,0}
     * - ...
     * - the ordinal '11' would correspond to indexes {2,3,2}
     */
    private fun indexes(ordinal: Int): Array<Int> {
        val indexes = arrayOf(sizes.size)

        var factor = 1
        for (i in sizes.size-1 downTo 0 step 1) {
            factor *= sizes[i]
            indexes[i] = ordinal.rem(factor)
        }

        return indexes
    }
}