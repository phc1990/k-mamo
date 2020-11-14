package org.phc1990.mammok.api

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

    /** Flag indicating whether a internal stop criterion was met. */
    val stop: Boolean

    /** Set of optimal candidates that constitute this iteration. */
    val optimalSet: Set<Candidate>
}