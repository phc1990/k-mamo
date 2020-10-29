package com.github.phc1990.kmamo

import com.github.phc1990.kmamo.algorithm.purerandomsearch.PureRandomSearch
import com.github.phc1990.kmamo.optimization.OptimizationCriterion
import com.github.phc1990.kmamo.topology.implementation.SpaceFactory


class HelloWorld {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            Random.setSeed(0)
            PureRandomSearch(1, "asdf")

            val word = "HI!"
            val charSpace = SpaceFactory.getCharSpace("ABCDEFGHIJKLMNOPQRSTUVWXYZ !")
            val algorithm = PureRandomSearch(10000000)

            val variables = List(word.length){ algorithm.addVariable("Var $it", charSpace)}
            val objective = algorithm.addObjective("Score", OptimizationCriterion.MAXIMIZE)

            algorithm.solve({ candidate -> var score = 0.0
                for (i in variables.indices) {
                    if (candidate.getVariable(variables[i]) == word[i]) { score++ } }
                candidate.setObjective(objective, score)
            }, {iteration -> var string = ""
                variables.forEach{v -> string += iteration.candidates[0].getVariable(v)}
                if (string == word) { iteration.forceStop() }
                if (iteration.isStop()) {
                    println("Iteration ${iteration.candidates[0].iterationIndex}: $string")
                }
            })
        }

    }

}