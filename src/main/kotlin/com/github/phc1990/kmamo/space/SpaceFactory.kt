package com.github.phc1990.kmamo.space

import com.github.phc1990.kmamo.Random
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * A [Space] factory.
 *
 * @see DoubleInterval
 * @see IntegerInterval
 * @see BooleanSpace
 *
 * @author Pau Hebrero Casasayas- May 26, 2020
 */
abstract class SpaceFactory  {

    companion object {

        /**
         * Creates a new space representing a double interval bounded by [lowerBoundary] and [upperBoundary]
         * (e.g. [-1,1]). */
        fun getDoubleInterval(name: String, lowerBoundary: Double, upperBoundary: Double): DoubleInterval =
                DoubleInterval(name, lowerBoundary, upperBoundary)

        /**
         * Creates a new space representing an integer interval bounded by [lowerBoundary] and [upperBoundary]
         * (e.g. [0-10].
         */
        fun getIntegerInterval(name: String, lowerBoundary: Int, upperBoundary: Int): IntegerInterval =
                IntegerInterval(name, lowerBoundary, upperBoundary)

        /** Creates a new boolean space (i.e. {false, true}). */
        fun getBooleanSpace(name: String): BooleanSpace = BooleanSpace(name)
    }
}

/**
 * A [Space] representing a real interval (e.g. [-0.1,0.1]).
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Interval_(mathematics)#Integer_intervals](https://en.wikipedia.org/wiki/Interval_(mathematics)#Integer_intervals)
 *
 * @see LinearSpace
 * @see MetricSpace
 * @see BoundedSpace
 *
 * @author Pau Hebrero Casasayas- May 25, 2020
 */
class DoubleInterval(override val name: String, override val lowerBoundary: Double, override val upperBoundary: Double):
        LinearSpace<Double>, MetricSpace<Double>, BoundedSpace<Double> {

    init {
        if (lowerBoundary > upperBoundary) throw java.lang.IllegalArgumentException("Boundaries are not consistent.")
    }

    override fun scale(scalar: Double, t: Double): Double = scalar * t
    override fun add(t1: Double, t2: Double): Double = t1 + t2
    override fun uniform(): Double = lowerBoundary + Random.uniformDouble() * (upperBoundary - lowerBoundary)
    override fun metric(t1: Double, t2: Double): Double = (t1 - t2).absoluteValue
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
 *
 * @author Pau Hebrero Casasayas- May 25, 2020
 */
class IntegerInterval(override val name: String,
                      override val lowerBoundary: Int, override val upperBoundary: Int,
                      private val size: Int = upperBoundary + 1 - lowerBoundary):
        FiniteSpace<Int>, LinearSpace<Int>, MetricSpace<Int>, BoundedSpace<Int> {

    init {
        if (lowerBoundary > upperBoundary) throw java.lang.IllegalArgumentException("Boundaries are not consistent.")
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
 * @author Pau Hebrero Casasayas- May 25, 2020
 */
class BooleanSpace(override val name: String): FiniteSpace<Boolean>, FlipSpace<Boolean> {

    override fun size(): Int = 2
    override fun get(i: Int): Boolean = i == 1
    override fun flip(t: Boolean): Boolean = !t
}

/**
 * A [Space] constituded by a finite number of possibilities.
 *
 * @see FiniteSpace
 * @author Pau Hebrero Casasayas- May 28, 2020
 */
class ListedSpace<T>(override val name: String, val values: List<T>): FiniteSpace<T> {

    /** Constructor. */
    constructor(name: String, vararg values: T): this(name, values.toList())

    init {
        if (values.isEmpty()) throw IllegalArgumentException("List cannot be empty.")
    }

    override fun size(): Int = values.size
    override fun get(i: Int): T = values[i]
}

