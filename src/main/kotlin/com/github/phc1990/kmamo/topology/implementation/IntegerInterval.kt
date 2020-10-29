package com.github.phc1990.kmamo.topology.implementation

import com.github.phc1990.kmamo.topology.BoundedSpace
import com.github.phc1990.kmamo.topology.FiniteSpace
import com.github.phc1990.kmamo.topology.LinearSpace
import com.github.phc1990.kmamo.topology.MetricSpace
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

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