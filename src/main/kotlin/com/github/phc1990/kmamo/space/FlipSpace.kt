package com.github.phc1990.kmamo.space

/**
 * A flip [Space]. Besides the inherited operations, its structure also supports:
 * - flip operation
 *
 * @see Space
 * @author Pau Hebrero Casasayas- May 25, 2020
 */
interface FlipSpace<T> : Space<T> {

    /** Returns the flipped value of [t]. */
    fun flip(t: T): T
}