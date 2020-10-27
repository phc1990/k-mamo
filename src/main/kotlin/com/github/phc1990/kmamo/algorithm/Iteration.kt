package com.github.phc1990.kmamo.algorithm

/**
 * An algorithm iteration. It contains information on:
 *
 * - its index (iteration number)
 * - its stopping criterion
 * - its candidate solutions
 *
 * Additionally, one can overwrite the stop criterion externally.
 *
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
interface Iteration {

    /** The index of the instance. */
    val index: Int

    /** List of evaluated candidates that constitute this iteration. */
    val candidates: List<Candidate>

    /** Side of the evaluated candidates. */
    fun size(): Int = candidates.size

    /** Returns whether the stop criterion has been met. */
    fun isStop(): Boolean
}

/**
 * Internal [Iteration] implementation.
 *
 * @see Iteration
 * @author Pau Hebrero Casasayas - Jun 1, 2020
 */
internal class InternalIteration(override val index: Int, override val candidates: List<Candidate>,
                                 private var stop: Boolean): Iteration {

    override fun isStop(): Boolean = stop

    override fun forceStop(): Boolean {

        if (stop) {
            return false
        }

        stop = true
        return true
    }
}
