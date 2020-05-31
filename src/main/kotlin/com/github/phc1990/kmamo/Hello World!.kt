package com.github.phc1990.kmamo

import com.github.phc1990.kmamo.algorithm.Mamo
import com.github.phc1990.kmamo.optimization.Algorithm
import com.github.phc1990.kmamo.optimization.VariableFactory
import com.github.phc1990.kmamo.space.FiniteSpace
import com.github.phc1990.kmamo.space.Space
import com.github.phc1990.kmamo.space.SpaceFactory


class `Hello World!` {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val charSpace = SpaceFactory.getCharSpace("ABCDEFGHIJKLMNOPQRSTUVWXYZ !")

            val algorithm: Algorithm<FiniteSpace<*>>

            val variable1 = algorithm.addVariable("Var-1", charSpace, Boolean.javaClass)
            val variable2 = algorithm.addVariable("Var-2", charSpace)
            val variable3 = algorithm.addVariable("Var-3", charSpace)
            val variable4 = algorithm.addVariable("Var-4", charSpace)
            val variable5 = algorithm.addVariable("Var-5", charSpace)
            val variable6 = algorithm.addVariable("Var-6", SpaceFactory.getBooleanSpace())



        }

    }

}