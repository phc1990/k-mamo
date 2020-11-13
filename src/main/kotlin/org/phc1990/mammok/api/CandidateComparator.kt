package org.phc1990.mammok.api

interface CandidateComparator: Comparator<Candidate> {
    val name: String
}