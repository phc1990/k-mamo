package org.phc1990.mammok.api

interface CandidateComparator {

    val name: String

    fun prevalence(c1: Candidate, c2: Candidate): Int

}