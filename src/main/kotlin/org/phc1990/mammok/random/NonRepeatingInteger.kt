package org.phc1990.mammok.random

internal class NonRepeatingInteger(private val min: Int, private val max: Int): Iterator<Int> {

    private val iterator: Iterator<Int>

    init {
        val list = IntArray(max-1-min, init = {i: Int -> i + min}).toMutableList()
        list.shuffle(Random.getJava())
        iterator = list.iterator()
    }

    override fun hasNext(): Boolean = iterator.hasNext()
    override fun next(): Int = iterator.next()
}