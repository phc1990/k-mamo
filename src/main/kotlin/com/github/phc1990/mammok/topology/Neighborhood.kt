package com.github.phc1990.mammok.topology

import com.github.phc1990.mammok.optimization.Candidate

/**
 * The neighborhood of a [Candidate] in the form of an [Iterator].
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Neighbourhood_(mathematics)](https://en.wikipedia.org/wiki/Neighbourhood_(mathematics))
 *
 * @see Candidate
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990)- Oct 31, 2020
 */
interface Neighborhood: Iterator<Candidate>