package org.phc1990.mammok.utils

import org.phc1990.mammok.topology.space.implementation.*

/**
 * A [Space] factory.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 26, 2020
 */
abstract class SpaceFactory  {

    companion object {

        /** Creates a [DoubleInterval] bounded by [lowerBoundary] and [upperBoundary] (e.g. {-1,1}). */
        fun getDoubleInterval(lowerBoundary: Double, upperBoundary: Double): DoubleInterval =
                DoubleInterval(lowerBoundary, upperBoundary)

        /** Creates a [IntegerInterval] bounded by [lowerBoundary] and [upperBoundary] (e.g. {0-10}). */
        fun getIntegerInterval(lowerBoundary: Int, upperBoundary: Int): IntegerInterval =
                IntegerInterval(lowerBoundary, upperBoundary)

        /** Creates a [BooleanSpace] (i.e. {false, true}). */
        fun getBooleanSpace(): BooleanSpace = BooleanSpace()

        /** Creates a [ListedSpace] (i.e. {'Monday','Tuesday',...,'Sunday'}). */
        fun <T> getListedSpace(values: List<T>): ListedSpace<T> = ListedSpace<T>(values)

        /** Creates a [ListedSpace] (i.e. {'Monday','Tuesday',...,'Sunday'}). */
        fun <T> getListedSpace(vararg values: T): ListedSpace<T> = getListedSpace(values.toList())

        /** Creates a [CharSpace] (e.g.e {A,B,C,...,X,Y,Z}). */
        fun getCharSpace(charSequence: CharSequence): CharSpace = CharSpace(charSequence)
    }
}

