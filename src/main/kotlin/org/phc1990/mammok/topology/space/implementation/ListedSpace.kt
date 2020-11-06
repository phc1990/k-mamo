package org.phc1990.mammok.topology.space.implementation

import org.phc1990.mammok.topology.space.FiniteSpace
import com.intellij.util.containers.toArray

/**
 * A [Space] constituted by a finite number of possibilities.
 *
 * @see FiniteSpace
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - May 28, 2020
 */
class ListedSpace<T>(private val values: List<T>,
                     private val neighborhoodFunction: ((point: T) -> Array<T>?)? = null): FiniteSpace<T> {

    init {
        if (values.isEmpty()) throw IllegalArgumentException("Set is null.")
    }

    fun indexOf(t: T): Int = values.indexOf(t)
    override fun size(): Int = values.size
    override fun get(i: Int): T = values[i]
    override fun neighbors(t: T): Array<T>? {
        neighborhoodFunction?.let {function -> function.invoke(t)?.let { array -> return array }}
        return null
    }
}