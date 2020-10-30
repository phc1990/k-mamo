package com.github.phc1990.mammok.topology

/**
 * A bounded [OrderedSpace]. Besides the inherited operations, its structure also supports:
 * - Object clipping
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Ordered_vector_space](https://en.wikipedia.org/wiki/Ordered_vector_space)
 *
 * @see OrderedSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
interface BoundedSpace<T: Comparable<T>>: OrderedSpace<T> {

    /** Lower boundary. */
    val lowerBoundary: T

    /** Upper boundary. */
    val upperBoundary: T

    /** Returns the clipped value of [t] between [lowerBoundary] and [upperBoundary] (both inclusive). */
    fun clip(t: T) = if (t < lowerBoundary) lowerBoundary else if (t > upperBoundary) upperBoundary else t
}