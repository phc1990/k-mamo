package com.github.phc1990.kmamo.topology

import com.github.phc1990.kmamo.optimization.Candidate

/**
 * A neighbor [Space]. Besides the inherited operations, its structure also supports:
 * - neighbor listing
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Neighbourhood_(mathematics)](https://en.wikipedia.org/wiki/Neighbourhood_(mathematics))
 *
 * @see Space
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990)- Sep 24, 2020
 */
interface NeighborSpace<T> : Space<T> {

}

/**
 * Sequential neighbor iterator.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Oct 27, 2020
 */
class SequentialNeighborIterator: Iterator<Candidate> {

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun next(): Candidate {
        TODO("Not yet implemented")
    }
}

/**
 * Random neighbor iterator.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Oct 27, 2020
 */
class RandomNeighborIterator: Iterator<Candidate> {

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun next(): Candidate {
        TODO("Not yet implemented")
    }
}