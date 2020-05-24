package com.github.phc1990.kmamo.space

import com.github.phc1990.kmamo.Random

class DoubleInterval(override val name: String, val lowerBoundary: Double, val upperBoundary: Double): VectorSpace<Double> {
    override fun multiply(scalar: Double, t: Double): Double = scalar * t
    override fun add(t1: Double, t2: Double): Double = t1 + t2
    override fun uniform(): Double = lowerBoundary + Random.uniformDouble() * (upperBoundary - lowerBoundary)
    override fun bound(t: Double): Double = if (t > upperBoundary) upperBoundary else if (t < lowerBoundary) lowerBoundary else t
}