package com.github.phc1990.mammok.topology.implementation

import com.github.phc1990.mammok.topology.FiniteSpace

/**
 * A [Space] constituted by a finite number of possibilities.
 *
 * @see FiniteSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 28, 2020
 */
class ListedSpace<T>(private val values: List<T>,
                     private val neighborhood: (point: T) -> Array<T>): FiniteSpace<T> {

    init {
        if (values.isEmpty()) throw IllegalArgumentException("Set is null.")
    }

    fun indexOf(t: T): Int = values.indexOf(t)
    override fun size(): Int = values.size
    override fun get(i: Int): T = values[i]
    override fun neighbors(t: T): Array<T> = neighborhood.invoke(t)
}