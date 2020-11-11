package org.phc1990.mammok.utils

import org.phc1990.mammok.topology.space.search.*

/**
 * A [Space] factory.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 26, 2020
 */
object Spaces  {

    /** Creates a [RealInterval] bounded by [lowerBoundary] and [upperBoundary] (e.g. {-1,1}). */
    fun getDoubleInterval(lowerBoundary: Double, upperBoundary: Double): RealInterval =
            RealInterval(lowerBoundary, upperBoundary)

    /** Creates a [IntegerInterval] bounded by [lowerBoundary] and [upperBoundary] (e.g. {0-10}). */
    fun getIntegerInterval(lowerBoundary: Int, upperBoundary: Int): IntegerInterval =
            IntegerInterval(lowerBoundary, upperBoundary)

    /** Creates a [BinarySpace] (i.e. {false, true}). */
    fun getBooleanSpace(): BinarySpace = BinarySpace()

    /** Creates a [ListedSpace] (i.e. {'Monday','Tuesday',...,'Sunday'}). */
    fun <T> getListedSpace(values: List<T>): ListedSpace<T> = ListedSpace<T>(values)

    /** Creates a [ListedSpace] (i.e. {'Monday','Tuesday',...,'Sunday'}). */
    fun <T> getListedSpace(vararg values: T): ListedSpace<T> = getListedSpace(values.toList())

    /** Creates a [CharSpace] (e.g.e {A,B,C,...,X,Y,Z}). */
    fun getCharSpace(charSequence: CharSequence): CharSpace = CharSpace(charSequence)

}

