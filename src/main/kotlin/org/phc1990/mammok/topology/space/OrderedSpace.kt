package org.phc1990.mammok.topology.space

/**
 * An ordered [Space]. Its structure supports:
 * - object comparability (e.g. t1 > t2)
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Total_order](https://en.wikipedia.org/wiki/Total_order)
 *
 * @see Space
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 25, 2020
 */
interface OrderedSpace<T: Comparable<T>>: Space<T>