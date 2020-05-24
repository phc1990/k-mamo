package com.github.phc1990.kmamo.space

import com.github.phc1990.kmamo.Random
import kotlin.math.roundToInt

class IntegerInterval(override val name: String, val lowerBoundary: Int, val upperBoundary: Int, private val size: Int = upperBoundary + 1 - lowerBoundary): FiniteSpace<Int>, VectorSpace<Int> {

    override fun size() = size
    override fun get(i: Int): Int = lowerBoundary + i

    override fun multiply(scalar: Double, t: Int): Int = (scalar * t).roundToInt();
    override fun add(t1: Int, t2: Int): Int = t1 + t2
    override fun uniform(): Int = lowerBoundary + Random.uniformInteger(size)
    override fun bound(t: Int): Int = if (t > upperBoundary) upperBoundary else if (t < lowerBoundary) lowerBoundary else t

}