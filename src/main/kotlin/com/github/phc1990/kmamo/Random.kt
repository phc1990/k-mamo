package com.github.phc1990.kmamo

import java.util.Random

class Random {

    companion object {
        private var RANDOM = Random()
        fun uniformDouble(): Double = RANDOM.nextDouble()
        fun uniformInteger(max: Int) = RANDOM.nextInt(max)
        fun uniformInteger(): Boolean = RANDOM.nextBoolean()
    }

}