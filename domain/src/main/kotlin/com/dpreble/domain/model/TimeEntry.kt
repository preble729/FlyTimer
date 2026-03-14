package com.dpreble.domain.model

data class TimeEntry(
    val id: Long,
    val athleteId: Long,
    val raceId: Long,
    val submittedTimeMs: Long
)
