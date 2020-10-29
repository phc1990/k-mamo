package com.github.phc1990.kmamo.topology.implementation

import com.github.phc1990.kmamo.topology.FiniteSpace

/**
 * A [Space] constituted by a set of characters.
 *
 * @see FiniteSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 30, 2020
 */
class CharSpace(val charSequence: CharSequence): FiniteSpace<Char> {

    init {
        if (charSequence.isEmpty()) throw IllegalArgumentException("Character sequence cannot be empty.")
    }

    override fun size(): Int = charSequence.length
    override fun get(i: Int): Char = charSequence[i]
}