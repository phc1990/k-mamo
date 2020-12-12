package org.phc1990.mammok.algorithm

import org.phc1990.mammok.api.Algorithm
import org.phc1990.mammok.topology.space.Space

abstract class AbstractAlgorithm<S: Space<Any>>: Algorithm<S> {

    protected var searchSpaces: MutableList<S> = mutableListOf()

    override fun addVariable(space: S): Int {
        searchSpaces.add(space)
        return searchSpaces.size-1
    }
}