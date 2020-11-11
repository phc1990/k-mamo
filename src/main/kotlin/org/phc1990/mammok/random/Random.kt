package org.phc1990.mammok.random

import java.util.Random

/**
 * Singleton used for all random operations.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
object Random {

    /** The underlying Java random instance. */
    private val random = Random()

    /** Returns the underlying Java random instance. */
    internal fun getJava(): Random = random

    /** Sets the [seed] of the random generator. */
    fun setSeed(seed: Long) = random.setSeed(seed)

    /** Returns a random uniformly-distributed double in the [0,1] interval. */
    internal fun uniformDouble(): Double = random.nextDouble()

    /** Returns a random uniformly-distributed integer in the [0, [max]) interval (i.e. excluding [max]). */
    internal fun uniformInteger(max: Int) = random.nextInt(max)

    /** Returns a random uniformly-distributed integer in the [[min], [max]) interval (i.e. excluding [max]). */
    internal fun uniformInteger(min: Int, max: Int) = min + random.nextInt(max - min)

    /**
     * Returns an iterator over a random non-repeated uniformly-distributed integer iterator in the [[min], [max])
     * interval (i.e. excluding [max]).
     */
    internal fun nonRepeatingUniformInteger(min: Int, max: Int): Iterator<Int> = NonRepeatingInteger(min, max)

    /** Returns a random uniformly distributed boolean in the [false, true] interval. */
    internal fun uniformBoolean(): Boolean = random.nextBoolean()
}