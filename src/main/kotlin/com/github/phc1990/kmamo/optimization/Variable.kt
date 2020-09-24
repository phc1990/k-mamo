package com.github.phc1990.kmamo.optimization

import com.github.phc1990.kmamo.space.Space

/**
 * An optimisation variable.
 *
 * @param T the variable type (e.g. [Double])
 * @author Pau Hebrero Casasayas - May 31, 2020
 */
interface Variable<T> {

    /** Variable's name. */
    val name: String
}

/**
 * A [Variable] factory.
 *
 * @see Variable
 * @author Pau Hebrero Casasayas - May 31, 2020
 */
internal abstract class VariableFactory {

    companion object {

        /** Returns a new instance of [Variable]. */
        fun <T, S: Space<T>> get(name: String, space: S): Variable<T> = DefaultVariable(name)

        /** Default implementation of [Variable], used by the [VariableFactory]. */
        private class DefaultVariable<T>(override val name: String): Variable<T>
    }
}
