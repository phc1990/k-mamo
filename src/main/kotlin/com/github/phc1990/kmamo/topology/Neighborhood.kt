package com.github.phc1990.kmamo.topology

import com.github.phc1990.kmamo.optimization.Candidate
import com.github.phc1990.kmamo.optimization.InternalCandidate
import com.github.phc1990.kmamo.optimization.Variable
import java.lang.RuntimeException

/**
 * A neighborhood.
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Neighbourhood_(mathematics)](https://en.wikipedia.org/wiki/Neighbourhood_(mathematics))
 *
 * @see Space
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990)- Sep 24, 2020
 */
internal class Neighborhood: Iterator<InternalCandidate> {

    private var iterationIndex: Int
    private var nextNeighborIndex: Int = 0
    private var variables = arrayOf<Variable<*>>()
    private var neighbors = arrayOf<Array<Any>>()
    private var sizes = arrayOf<Int>()
    private var totalNeighbors: Int = 0

    constructor(point: InternalCandidate) {

        point.variables.entries.forEach { e ->

            point.getVariable(e.key)?.let {
                var array = arrayOf<Any>()

                // Add the point itself (index 0 of the array)
                array += it

                // Add the neighbours to the point (index 1,2,...)
                (e.key.space as Space<Any>).neighbors(it).forEach { n -> array += n }

                sizes += array.size
                neighbors += array
                variables += e.key
            }
        }

        if (neighbors.size != point.variables.size ||
                sizes.size != point.variables.size ||
                variables.size != point.variables.size) {
            throw RuntimeException("Neighborhood dimensions does not match variable space dimension")
        }

        totalNeighbors = 1
        for (size in sizes) {
            totalNeighbors *= size
        }

        iterationIndex = point.iterationIndex + 1

    }

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun next(): InternalCandidate {
        TODO("Not yet implemented")
    }

    private fun candidate(n: Int): InternalCandidate {
        val indexes = indexes(n)

        val map = mutableMapOf<Variable<*>, Any>()

        for (i in variables.indices) {
            map[variables[i]] = neighbors[i][indexes[i]]
        }

        val candidate = InternalCandidate(iterationIndex, nextNeighborIndex, map)
        nextNeighborIndex ++
        return candidate
    }

    private fun indexes(n: Int): IntArray {
        val indexes = IntArray(sizes.size)

        var factor = 1
        for (i in sizes.size-1 downTo 0 step 1) {
            factor *= sizes[i]
            indexes[i] = n.rem(factor)
        }

        return indexes
    }
}