package com.github.phc1990.mammok.topology

/**
 * A linear [Space] (a.k.a. vector space). Besides the inherited operations, its structure also supports Linear Algebra
 * operations:
 * - addition of two objects (t1 + t2)
 * - multiplication of one object by a scalar, "scaling" (s * t1)
 *
 * Notes:
 * - The nomenclature "linear space" has been chosen over "vector space" to avoid confusion with software vector
 * structures.
 * - The **operations must return a new instance** of an object of the set.
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Vector_space](https://en.wikipedia.org/wiki/Vector_space)
 *
 * @see Space
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 24, 2020
 */
interface LinearSpace<T>: Space<T> {

    /** Returns the resulting instance of the adding of two objects of the space set. */
    fun add(t1: T, t2: T): T

    /** Returns the resulting instance of scaling (i.e. multiplying by [scalar]) an object of the space set. */
    fun scale(scalar: Double, t: T): T

    /** Returns the resulting instance of negating (i.e. scaling by -1) an object of the space set. */
    fun negate(t: T) = scale(-1.0, t)

    /** Returns the resulting instance of subtracting two objects (i.e. [t1] - [t2]) of the space set. */
    fun subtract(t1: T, t2: T): T = add(t1, negate(t2))

    /** Returns the resulting instance of dividing (i.e. scaling by 1/[scalar]) an object of the set. */
    fun divide(scalar: Double, t:T): T = scale(1.0/scalar, t)
}
