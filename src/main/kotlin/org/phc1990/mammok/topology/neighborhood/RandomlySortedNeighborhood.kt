package org.phc1990.mammok.topology.neighborhood

import org.phc1990.mammok.optimization.Candidate
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.utils.random.Random
import org.phc1990.mammok.topology.space.Space

/**
 * A [Neighborhood] that provides randomly sorted neighbors.
 *
 * @see Neighborhood
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990)- Oct 31, 2020
 */
internal class RandomlySortedNeighborhood: Neighborhood {

    private val point: Candidate
    private val iterationIndex: Int
    private var nextCandidateIndex: Int = 0
    private val numberOfObjectives: Int

    /** A matrix containing each of the neighbor values for each variable. This contains all the potential values for
     * each variable, that, in combination, can spawn any neighbor. (e.g. {{-1,0,+1}, {"a","b"}}). For each variable,
     * the first value [0] is that of the point. */
    private var values = arrayOf<Array<Any>>()

    /** An array containing the amount of potential values for each one of the variables (e.g. {3,2}). */
    private var sizes = arrayOf<Int>()

    /** An iterator that provides the next neighbor ordinal. */
    private val ordinalIterator: Iterator<Int>

    constructor(candidate: Candidate, variables: Array<Space<Any>>, numberOfObjectives: Int) {

        point = candidate
        this.numberOfObjectives = numberOfObjectives

        // Set the iteration index for the neighbors
        iterationIndex = candidate.iterationIndex + 1

        for (i in variables.indices) {
            // Neighbors array
            var array = arrayOf<Any>()

            // Get the point
            val point = candidate.getVariable(i, Any::class.java)

            // Add the point itself (index 0 of the array)
            array += point

            // Add the neighbors to the point (index 1,2,...)
            variables[i].neighbors(point)?.forEach { n -> array += n }

            sizes += array.size
            values += array
        }

        // Compute the number of total neighbors
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
    private fun candidate(indexes: IntArray): Candidate {
        val candidate = InternalCandidate(iterationIndex, nextCandidateIndex,
                Array(indexes.size) { i -> values[i][indexes[i]]}, numberOfObjectives)
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
     * - the ordinal '4' would correspond to indexes {0,2,0}
     * - the ordinal '5' would correspond to indexes {0,2,1}
     * - the ordinal '6' would correspond to indexes {1,0,0}
     * - ...
     * - the ordinal '11' would correspond to indexes {1,2,1}
     */
    private fun indexes(ordinal: Int): IntArray {
        val indexes = IntArray(sizes.size)
        var divident = ordinal
        var divisor = 1
        for (i in sizes.indices) {
            divisor *= sizes[i]
        }
        for (i in sizes.indices) {
            divisor /= sizes[i]
            indexes[i] = divident/divisor
            divident = ordinal.rem(divisor)
        }
        return indexes
    }
}