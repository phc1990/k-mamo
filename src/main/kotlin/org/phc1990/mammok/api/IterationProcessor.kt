package org.phc1990.mammok.api

interface IterationProcessor {

    fun process(iteration: Iteration): Boolean

}