package org.phc1990.mammok.algorithm.particleswarm

import org.phc1990.mammok.algorithm.AbstractAlgorithm
import org.phc1990.mammok.api.CandidateComparator
import org.phc1990.mammok.api.CandidateEvaluator
import org.phc1990.mammok.api.IterationProcessor
import org.phc1990.mammok.api.OptimalSetPruner
import org.phc1990.mammok.optimization.InternalCandidate
import org.phc1990.mammok.topology.space.LinearSpace

class ParticleSwarm(private val populationSize: Int, private val maxGenerations: Int): AbstractAlgorithm<LinearSpace<Any>>() {

    private lateinit var best: Array<InternalCandidate>
    override val name: String = "Particle Swarm"

    override fun run(evaluator: CandidateEvaluator,
                     comparator: CandidateComparator,
                     pruner: OptimalSetPruner,
                     processor: IterationProcessor) {

        // Initialise population
        best = Array(populationSize) { _ -> InternalCandidate.uniform (searchSpaces).also { evaluator.evaluate(it) }}

        var i = 0
        var stop = false
        while (!stop) {

            val generation = Array<InternalCandidate>(populationSize) {i ->
                val iBest = best[i]
                val oBest = best[i]
                InternalCandidate(Array(iBest.variablesSize()) { v ->
                    val space = searchSpaces[v]
                    
                    space.iBest.variables[v]
                }).also { evaluator.evaluate(it) }
            }

            i++
        }



        TODO("Not yet implemented")
    }


}