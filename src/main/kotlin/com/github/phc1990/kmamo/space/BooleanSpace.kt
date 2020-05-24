package com.github.phc1990.kmamo.space

class BooleanSpace(override val name: String, override val state0: Boolean, override val state1: Boolean = !state0): BinarySpace<Boolean> {}