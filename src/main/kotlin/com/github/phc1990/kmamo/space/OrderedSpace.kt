package com.github.phc1990.kmamo.space

/**
 * An ordered [Space]. Its structure supports:
 * - object comparability (e.g. t1 > t2)
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Total_order](https://en.wikipedia.org/wiki/Total_order)
 *
 * @see Space
 * @author Pau Hebrero Casasayas - May 25, 2020
 */
interface OrderedSpace<T: Comparable<T>>: Space<T>