package com.dpreble.domain.race.models

data class Race(
    val id: Long,
    val raceName: String,
    val lapsPerAthlete: List<Int>
)