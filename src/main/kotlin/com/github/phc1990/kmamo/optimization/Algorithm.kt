package com.github.phc1990.kmamo.optimization

import com.github.phc1990.kmamo.space.Space

interface Algorithm<V : Space<*>> {

    fun <T, S: V> addVariable(name: String, space: S, clazz: Class<T>): Variable<T> {
        val variable = VariableFactory.get(name, space)
        addVariable(variable)
        return variable as Variable<T>
    }

}