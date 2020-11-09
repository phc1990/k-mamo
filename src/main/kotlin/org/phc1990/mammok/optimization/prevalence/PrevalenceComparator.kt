package org.phc1990.mammok.optimization.prevalence

import org.phc1990.mammok.optimization.Candidate

/**
 * Prevalence comparator.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 9, 2020
 */
interface PrevalenceComparator: Comparator<Candidate> {
    val name: String
}

