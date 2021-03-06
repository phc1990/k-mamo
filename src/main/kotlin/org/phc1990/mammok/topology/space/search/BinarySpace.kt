package org.phc1990.mammok.topology.space.search

import org.phc1990.mammok.topology.space.FiniteSpace
import org.phc1990.mammok.topology.space.FlipSpace

/**
 * A [Space] representing a binary choice (i.e. 'true' or 'false').
 *
 * @see FiniteSpace
 * @see FlipSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
class BinarySpace(private val allowsNeighbors: Boolean = false): FiniteSpace<Boolean>, FlipSpace<Boolean> {

    override fun size(): Int = 2
    override fun get(i: Int): Boolean = i == 1
    override fun flip(t: Boolean): Boolean = !t
    override fun neighbors(t: Boolean): Array<Boolean>? = if (allowsNeighbors) { arrayOf(!t)} else { null }
}