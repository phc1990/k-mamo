package com.github.phc1990.kmamo.space

/**
 * A mathematical space. A mathematical space is constituted by:
 *
 * 1. A defined __set of objects__, finite or infinite (e.g. a binary choice {false,true}, a real interval [-1,1])
 * 2. A defined __structure__, i.e. a group of features (e.g. operations, relations, etc.)
 *
 * References:
 * - [https://en.wikipedia.org/wiki/Space_(mathematics)](https://en.wikipedia.org/wiki/Space_(mathematics))
 * - [https://en.wikipedia.org/wiki/Set_(mathematics)](https://en.wikipedia.org/wiki/Set_(mathematics))
 * - [https://en.wikipedia.org/wiki/Mathematical_structure](https://en.wikipedia.org/wiki/Mathematical_structure)
 *
 * @author Pau Hebrero Casasayas- May 24, 2020
 */
interface Space<out T> {

    /** Returns a random-uniformly distributed object of the set. */
    fun uniform(): T
}
