package com.github.phc1990.kmamo.space

class DiscreteSpace<T>(override val name: String, val values: List<T>): FiniteSpace<T> {
    constructor(name: String, vararg values: T): this(name, values.toList())
    override fun size(): Int = values.size
    override fun get(i: Int): T = values[i]
}