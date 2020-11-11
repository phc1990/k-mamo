package org.phc1990.mammok.topology.space.search

import org.phc1990.mammok.topology.space.BoundedSpace
import org.phc1990.mammok.topology.space.FiniteSpace
import org.phc1990.mammok.topology.space.LinearSpace
import org.phc1990.mammok.topology.space.MetricSpace
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
class IntegerInterval(private val lowerBoundary: Int, private val upperBoundary: Int,
                      private val neighborhoodRadius: Int = 0, private val loop: Boolean = false):
        FiniteSpace<Int>, LinearSpace<Int>, MetricSpace<Int>, BoundedSpace<Int> {


    private val span: Int
    private val size: Int

    init {
        if (lowerBoundary > upperBoundary)
            throw IllegalArgumentException("Interval boundaries are not consistent.")
        if (neighborhoodRadius < 0)
            throw IllegalArgumentException("Neighborhood radius has to be greater or equal than 0.")
        span = upperBoundary - lowerBoundary
        if (span == 0) {
            throw IllegalArgumentException("Set is null.")
        }
        size = span + 1
    }

    override fun size() = size
    override fun get(i: Int): Int = lowerBoundary + i
    override fun scale(scalar: Double, t: Int): Int = (scalar * t).roundToInt();
    override fun add(t1: Int, t2: Int): Int = t1 + t2
    override fun metric(t1: Int, t2: Int): Double = (t1 - t2).toDouble().absoluteValue

    override fun clip(t: Int): Int {
        if (!loop)
            return if (t < lowerBoundary) lowerBoundary else if (t > upperBoundary) upperBoundary else t

        // Looped space
        if (t in lowerBoundary..upperBoundary)
            return t
        if (t < lowerBoundary)
            return upperBoundary + ((t - lowerBoundary) % span)
        // Above upper boundary
        return lowerBoundary + ((t - upperBoundary) % span)
    }

    override fun neighbors(t: Int): Array<Int>? {
        if (neighborhoodRadius > 0) {
            var neighbors = arrayOf<Int>()
            clip(t - neighborhoodRadius).also { if (it != t) neighbors += it }
            clip(t + neighborhoodRadius).also { if (it != t) neighbors += it }
            return neighbors
        }
        return null
    }
}