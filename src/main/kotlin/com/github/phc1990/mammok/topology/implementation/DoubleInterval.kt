package com.github.phc1990.mammok.topology.implementation

import com.github.phc1990.mammok.random.Random
import com.github.phc1990.mammok.topology.BoundedSpace
import com.github.phc1990.mammok.topology.LinearSpace
import com.github.phc1990.mammok.topology.MetricSpace
import kotlin.math.absoluteValue

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
class DoubleInterval(private val lowerBoundary: Double, private val upperBoundary: Double,
                     private val neighborhoodRadius: Double = 0.0, private val loop: Boolean = false):
        LinearSpace<Double>, MetricSpace<Double>, BoundedSpace<Double> {

    private val span: Double

    init {
        if (lowerBoundary > upperBoundary)
            throw IllegalArgumentException("Interval boundaries are not consistent.")
        if (neighborhoodRadius < 0)
            throw IllegalArgumentException("Neighborhood radius has to be greater or equal than 0.")
        span = upperBoundary - lowerBoundary
        if (span == 0.0) {
            throw IllegalArgumentException("Set is null.")
        }
    }

    override fun scale(scalar: Double, t: Double): Double = scalar * t
    override fun add(t1: Double, t2: Double): Double = t1 + t2
    override fun uniform(): Double = lowerBoundary + Random.uniformDouble() * (upperBoundary - lowerBoundary)
    override fun metric(t1: Double, t2: Double): Double = (t1 - t2).absoluteValue

    override fun clip(t: Double): Double {
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

    override fun neighbors(t: Double): Array<Double> {
        if (neighborhoodRadius > 0) {
            var neighbors = arrayOf<Double>()
            clip(t - neighborhoodRadius).also { if (it != t) neighbors += it }
            clip(t + neighborhoodRadius).also { if (it != t) neighbors += it }
            return neighbors
        }
        return emptyArray()
    }
}