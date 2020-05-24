package com.github.phc1990.kmamo.space

import com.github.phc1990.kmamo.Random

interface FiniteSpace<T>: Space<T> {
    fun size(): Int
    operator fun get(i: Int): T
    override fun uniform() = this[Random.uniformInteger(size())]
}