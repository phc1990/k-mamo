package com.github.phc1990.kmamo.optimization

import com.github.phc1990.kmamo.topology.Space

/**
 * An optimisation variable.
 *
 * @param T the variable type (e.g. [Double])
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 31, 2020
 */
interface Variable<T> {

    /** Variable's name. */
    val name: String

    /** Variable's space. */
    val space: Space<T>
}

/**
 * A [Variable] factory.
 *
 * @see Variable
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 31, 2020
 */
internal abstract class VariableFactory {

    companion object {

        /** Returns a new instance of [Variable]. */
        fun <T, S: Space<T>> get(name: String, space: S): Variable<T> = DefaultVariable(name, space)

        /** Default implementation of [Variable], used by the [VariableFactory]. */
        private class DefaultVariable<T>(override val name: String, override val space: Space<T>): Variable<T>
    }
}
