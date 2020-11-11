package org.phc1990.mammok.utils

import org.phc1990.mammok.api.IterationProcessor
import org.phc1990.mammok.api.Iteration
import java.lang.Appendable

object IterationProcessors {

    fun getNone(): IterationProcessor = object : IterationProcessor {
        override fun process(iteration: Iteration): Boolean = false
    }

    fun getLogger(appendable: Appendable = System.out): IterationProcessor = object : IterationProcessor {
        override fun process(iteration: Iteration): Boolean {
            appendable.appendln("Iteration: ${iteration.index}...")
            if (iteration.stop) {
                appendable.appendln("********")
                appendable.appendln("Algorithm stopped!")
                var i = 1
                iteration.optimalSet.forEach { o ->
                    appendable.appendln("********")
                    appendable.appendln("Optimal candidate $i:")
                    for (j in 0 until o.variablesSize()) {
                        appendable.appendln("Var $j = ${o.getVariable(j, Any::class.java)}")}
                    for (j in o.objectives.indices) {
                        appendable.appendln("Obj $j = ${o.objectives[j]}")}
                    i++
                }
            }
            return false
        }
    }
}