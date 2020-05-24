package com.github.phc1990.kmamo.space

interface BinarySpace<T>: FiniteSpace<T> {
    val state0: T
    val state1: T
    override fun size(): Int =2
    override fun get(i: Int): T = if (i == 0) state0 else state1
    fun flip(t: T): T = if (t == state0) state1 else state0
}