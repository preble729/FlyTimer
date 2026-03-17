package com.dpreble.domain.times.models

data class TimeEntry(
    val id: Long,
    val athleteId: Long,
    val raceId: Long,
    val submittedTimeMs: Long
)