package com.github.phc1990.kmamo

import com.github.phc1990.kmamo.algorithm.PureRandomSearch
import com.github.phc1990.kmamo.optimization.OptimizationCriterion
import com.github.phc1990.kmamo.optimization.Variable
import com.github.phc1990.kmamo.space.SpaceFactory


class HelloWorld {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            Random.setSeed(0)

            val word = "HELLO!"
            val charSpace = SpaceFactory.getCharSpace("ABCDEFGHIJKLMNOPQRSTUVWXYZ !")
            val algorithm = PureRandomSearch(10000000)

            val variables = List(word.length){ algorithm.addVariable("Var $it", charSpace)}
            val objective = algorithm.addObjective("Score", OptimizationCriterion.MAXIMIZE)

            algorithm.solve({ candidate -> var score = 0.0
                for (i in variables.indices) {
                    if (candidate.getVariable(variables[i]) == word[i]) { score++ } }
                candidate.setObjective(objective, score)
            }, {iteration -> if (iteration.stopped) {
                    var string = ""
                    variables.forEach{v -> string += iteration.candidates[0].getVariable(v)}
                    println("Iteration ${iteration.candidates[0].iterationIndex}: $string")
                }
            })
        }

    }

}