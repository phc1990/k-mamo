package org.phc1990.mammok.optimization

/**
 * An algorithm iteration. It contains information on:
 *
 * - its index (iteration number)
 * - its stopping criterion
 * - its candidate solutions
 *
 * Additionally, one can overwrite the stop criterion externally.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
interface Iteration {

    /** The index of the instance. */
    val index: Int

    /** Flag indicating whether a internal stop criterion was met. */
    val stop: Boolean

    /** Set of optimal candidates that constitute this iteration. */
    val optimalSet: Set<Candidate>
}

/**
 * Internal [Iteration] implementation.
 *
 * @see Iteration
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
internal data class InternalIteration(  override val index: Int, override val stop: Boolean,
                                        override val optimalSet: Set<Candidate>): Iteration
