package com.github.phc1990.kmamo.algorithm.hillclimbing

import com.github.phc1990.kmamo.algorithm.Algorithm
import com.github.phc1990.kmamo.algorithm.Candidate
import com.github.phc1990.kmamo.optimization.Objective

class SimpleHillClimbing(val objective: Objective): AbstractHillClimbing(objective) {

    override fun spawnNeighborIterator(node: Candidate): Iterator<Candidate> {
        TODO("Not yet implemented")
    }

    override val name: String = "Simple Hill Climbing"
}