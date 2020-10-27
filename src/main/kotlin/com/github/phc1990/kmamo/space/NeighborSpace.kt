package com.github.phc1990.kmamo.space

/**
 * A neighbor [Space]. Besides the inherited operations, its structure also supports:
 * - neighbor listing
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Neighbourhood_(mathematics)](https://en.wikipedia.org/wiki/Neighbourhood_(mathematics))
 *
 * @see Space
 * @author Pau Hebrero Casasayas- Sep 24, 2020
 */
interface NeighborSpace<T> : Space<T> {

    /** Returns the neighbors of [t]. */
    fun neighbors(t: T): List<T>
}