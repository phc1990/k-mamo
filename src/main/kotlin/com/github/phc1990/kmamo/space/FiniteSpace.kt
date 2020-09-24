package com.github.phc1990.kmamo.space

import com.github.phc1990.kmamo.Random

/**
 * A finite [Space]. Its set contains a finite number of objects. Besides the inherited operations, its structure also
 * supports:
 * - index access
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Finite_topological_space](https://en.wikipedia.org/wiki/Finite_topological_space)
 *
 * @see Space
 * @author Pau Hebrero Casasayas - May 24, 2020
 */
interface FiniteSpace<T>: Space<T> {

    /** Returns the size of the finite set. */
    fun size(): Int

    /** Index access operation. */
    operator fun get(i: Int): T

    override fun uniform() = this[Random.uniformInteger(size())]
}