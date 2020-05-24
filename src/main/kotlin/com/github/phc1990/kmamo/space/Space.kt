package com.github.phc1990.kmamo.space

interface Space<T> {
    val name: String
    fun uniform(): T
}