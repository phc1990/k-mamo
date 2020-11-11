package org.phc1990.mammok.algorithm

import org.phc1990.mammok.api.Candidate
import org.phc1990.mammok.api.Iteration

/**
 * Internal [Iteration] implementation.
 *
 * @see Iteration
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
internal class InternalIteration(  override val index: Int, override val stop: Boolean,
                                   override val optimalSet: Set<Candidate>): Iteration