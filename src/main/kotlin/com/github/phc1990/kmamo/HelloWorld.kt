package com.github.phc1990.kmamo

import com.github.phc1990.kmamo.algorithm.PureRandomSearch
import com.github.phc1990.kmamo.optimization.Variable
import com.github.phc1990.kmamo.space.SpaceFactory


class HelloWorld {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            println("Started")

            val charSpace = SpaceFactory.getCharSpace("ABCDEFGHIJKLMNOPQRSTUVWXYZ !")

            val algorithm = PureRandomSearch(10)

            val variables = List<Variable<Char>>(12){algorithm.addVariable("Char-$it", charSpace)}
            val objective =

            algorithm.solve({candidate ->
                variables.forEach { v -> print(candidate.getVariable(v))}
                println()
            }, {})

            println("Finished")
        }

    }

}