package com.github.phc1990.kmamo

import com.github.phc1990.kmamo.space.Space

interface Mamo<V: Space<Any>, O: Space<Any>> {
    fun addVariable(variable: V): Mamo<V,O>
    fun addObjective(objective: O): Mamo<V,O>
    fun solve(evaluator: (evaluationBroker: EvaluationBroker) -> Unit,
              processor: (evaluation: Evaluation) -> Unit = {})
}

interface VariableBroker {
    fun evaluationNumber(): Int
    fun iterationNumber(): Int
    fun <T, S: Space<T>> getVariable(variable: S): T
}

interface EvaluationBroker: VariableBroker {
    fun <T, S: Space<T>> setObjective(objective: S, value: T): EvaluationBroker
}

interface Solution: VariableBroker {
    fun <T, S: Space<T>> getObjective(objective: S): T
}

interface Evaluation {
    fun iterationNumber(): Int
    fun hasFinished(): Boolean
    fun solutions(): List<Solution>
}
