package com.github.phc1990.mammok.topology.implementation

import com.github.phc1990.mammok.topology.FiniteSpace

/**
 * A [Space] constituted by a finite number of possibilities.
 *
 * @see FiniteSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 28, 2020
 */
class ListedSpace<T>(val values: List<T>): FiniteSpace<T> {

    init {
        if (values.isEmpty()) throw IllegalArgumentException("List cannot be empty.")
    }

    override fun size(): Int = values.size
    override fun get(i: Int): T = values[i]
}