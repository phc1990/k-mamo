package com.github.phc1990.kmamo.space

/**
 * A metric [Space]. Its structure supports:
 * - metric (distance) between two objects
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Metric_space](https://en.wikipedia.org/wiki/Metric_space)
 *
 * @see Space
 * @author Pau Hebrero Casasayas - May 26, 2020
 */
interface MetricSpace<T>: Space<T> {

    /** Returns the metric between [t1] and [t2]. */
    fun metric(t1: T, t2: T): T
}