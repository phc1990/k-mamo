package org.phc1990.mammok.a


abstract class A{
    abstract fun sum(a: A): A
}



interface LinearStructure<K> {
    fun sum(k1: K, k2: K): K
}

class Real: LinearStructure<Double> {

}


interface AlgInterface<K> {
    fun <T> addVariable(name: String, structure: K)
}

class AlgClass: AlgInterface<LinearStructure<*>> {
    override fun <T> addVariable(name: String, structure: LinearStructure<T>) {
        TODO("Not yet implemented")
    }

}

class Main {


    fun main() {

    }

}