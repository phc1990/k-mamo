package com.github.phc1990.kmamo

import java.util.Random

/**
 * Singleton used for all random operations.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
object Random {

    /** The underlying random instance. */
    private val random = Random()

    /** Sets the [seed] of the random generator. */
    fun setSeed(seed: Long) = random.setSeed(seed)

    /** Returns a random uniformly-distributed double in the {0,1} interval. */
    fun uniformDouble(): Double = random.nextDouble()

    /** Returns a random uniformly distributed integer in the {0, [max]} interval (excluding [max]). */
    fun uniformInteger(max: Int) = random.nextInt(max)

    /** Returns a random uniformly distributed boolean in the {false, true} interval. */
    fun uniformBoolean(): Boolean = random.nextBoolean()
}