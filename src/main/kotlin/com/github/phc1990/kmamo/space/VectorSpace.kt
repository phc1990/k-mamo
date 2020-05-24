package com.github.phc1990.kmamo.space

interface VectorSpace<T>: Space<T> {
    fun multiply(scalar: Double, t: T): T
    fun add(t1: T, t2: T): T
    fun negate(t: T) = multiply(-1.0, t)
    fun minus(t1: T, t2: T): T = add(t1, negate(t2))
    fun bound(t: T): T
}