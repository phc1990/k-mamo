package org.phc1990.mammok.topology.space

/**
 * A metric [Space]. Its structure supports:
 * - metric (distance) between two objects
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Metric_space](https://en.wikipedia.org/wiki/Metric_space)
 *
 * @see Space
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 26, 2020
 */
interface MetricSpace<T>: Space<T> {

    /** Returns the metric between [t1] and [t2]. */
    fun metric(t1: T, t2: T): Double
}