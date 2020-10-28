package com.github.phc1990.kmamo.topology

import com.github.phc1990.kmamo.Random
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * A [Space] factory.
 *
 * @see DoubleInterval
 * @see IntegerInterval
 * @see BooleanSpace
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

/**
 * A [Space] representing a real interval (e.g. {-0.1,0.1}).
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Interval_(mathematics)#Integer_intervals](https://en.wikipedia.org/wiki/Interval_(mathematics)#Integer_intervals)
 *
 * @see LinearSpace
 * @see MetricSpace
 * @see BoundedSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
class DoubleInterval(override val lowerBoundary: Double, override val upperBoundary: Double,
                     private val neighborhoodRadius: Double? = null):
        LinearSpace<Double>, MetricSpace<Double>, BoundedSpace<Double> {

    init {
        if (lowerBoundary > upperBoundary) throw IllegalArgumentException("Interval boundaries are not consistent.")
        neighborhoodRadius?.let { if (it <= 0) throw IllegalArgumentException("Neighborhood radius has to be greater than 0.") }
    }

    override fun scale(scalar: Double, t: Double): Double = scalar * t
    override fun add(t1: Double, t2: Double): Double = t1 + t2
    override fun uniform(): Double = lowerBoundary + Random.uniformDouble() * (upperBoundary - lowerBoundary)
    override fun metric(t1: Double, t2: Double): Double = (t1 - t2).absoluteValue
    override fun neighbors(t: Double): List<Double> {
        neighborhoodRadius?.let {
            val neighbors = mutableListOf<Double>()
            clip(t - neighborhoodRadius).also { if (it != lowerBoundary && it != upperBoundary) neighbors.add(it) }
            clip(t + neighborhoodRadius).also { if (it != lowerBoundary && it != upperBoundary) neighbors.add(it) }
            return neighbors
        }
        return emptyList()
    }
}

/**
 * A [Space] representing an integer interval (e.g. [0,9] or [-1,1]). It implements both [LinearSpace] and
 * [FiniteSpace], the use of it as a [FiniteSpace] is recommended if the number of objects is 'small'.
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Interval_(mathematics)#Integer_intervals](https://en.wikipedia.org/wiki/Interval_(mathematics)#Integer_intervals)
 *
 * @see FiniteSpace
 * @see LinearSpace
 * @see MetricSpace
 * @see BoundedSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
class IntegerInterval(override val lowerBoundary: Int, override val upperBoundary: Int,
                      private val size: Int = upperBoundary + 1 - lowerBoundary):
        FiniteSpace<Int>, LinearSpace<Int>, MetricSpace<Int>, BoundedSpace<Int> {

    init {
        if (lowerBoundary > upperBoundary) throw IllegalArgumentException("Interval boundaries are not consistent.")
    }

    override fun size() = size
    override fun get(i: Int): Int = lowerBoundary + i
    override fun scale(scalar: Double, t: Int): Int = (scalar * t).roundToInt();
    override fun add(t1: Int, t2: Int): Int = t1 + t2
    override fun metric(t1: Int, t2: Int): Int = (t1 - t2).absoluteValue
}

/**
 * A [Space] representing a boolean choice (i.e. 'true' or 'false').
 *
 * @see FiniteSpace
 * @see FlipSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
class BooleanSpace: FiniteSpace<Boolean>, FlipSpace<Boolean> {

    override fun size(): Int = 2
    override fun get(i: Int): Boolean = i == 1
    override fun flip(t: Boolean): Boolean = !t
}

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

