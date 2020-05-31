package com.github.phc1990.kmamo.optimization

import com.github.phc1990.kmamo.space.Space

interface Variable<T> {
    val name: String
    val space: Space<T>
}

abstract class VariableFactory {

    companion object {
        fun <T, S: Space<T>> get(name: String, space: S): Variable<T> = DefaultVariable(name, space)
        private data class DefaultVariable<T>(override val name: String, override val space: Space<T>): Variable<T>
    }

}
