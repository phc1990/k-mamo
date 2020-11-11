package org.phc1990.mammok.topology.space.search

import org.phc1990.mammok.topology.space.FiniteSpace

/**
 * A [Space] constituted by a set of characters.
 *
 * @see FiniteSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 30, 2020
 */
class CharSpace(private val charSequence: CharSequence,
                private val neighborhoodFunction: ((point: Char) -> Array<Char>?)? = null): FiniteSpace<Char> {

    init {
        if (charSequence.isEmpty()) throw IllegalArgumentException("Character sequence cannot be empty.")
    }

    override fun size(): Int = charSequence.length
    override fun get(i: Int): Char = charSequence[i]
    override fun neighbors(t: Char): Array<Char>? {
        neighborhoodFunction?.let {function -> function.invoke(t)?.let { array -> return array }}
        return null
    }
}