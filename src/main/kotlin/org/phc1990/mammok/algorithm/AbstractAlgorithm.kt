package org.phc1990.mammok.algorithm

import org.phc1990.mammok.topology.space.Space

abstract class AbstractAlgorithm<S: Space<Any>>: Algorithm<S> {

    protected var variables: Array<Space<Any>> = arrayOf()

    override fun addVariable(space: S): Int {
        variables += space
        return variables.size-1
    }
}