package org.phc1990.mammok.optimization

import org.phc1990.mammok.topology.space.LinearSpace
import org.phc1990.mammok.topology.space.Space

interface Problem<S: Space<*>> {


    fun <S> addVariable(name: String, space: S)


    fun solve(a: A<S>, evaluator: BlackBoxEvaluator, processor: IterationProcessor)
}

interface A<S: Space<*>> {



}