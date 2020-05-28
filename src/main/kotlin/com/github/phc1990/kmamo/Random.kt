package com.github.phc1990.kmamo

import java.util.Random

/**
 * Singleton used for all random operations.
 *
 * @author Pau Hebrero Casasayas - May 25, 2020
 */
object Random {

    /** The underlying random instance. */
    var RANDOM = Random()

    /** Returns a random uniformly-distributed double in the {0,1} interval. */
    fun uniformDouble(): Double = RANDOM.nextDouble()

    /** Returns a random uniformly distributed integer in the {0, [max]} interval (excluding [max]. */
    fun uniformBoolean(max: Int) = RANDOM.nextInt(max)

    /** Returns a random uniformly distributed boolean in the {false, true} interval. */
    fun uniformBoolean(): Boolean = RANDOM.nextBoolean()
}