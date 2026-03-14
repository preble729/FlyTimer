package com.dpreble.domain.model

data class AthleteDetails(
    val athlete: Athlete,
    val bestTimes: List<AthleteBestTime>,
    val raceHistory: List<AthleteRaceHistoryEntry>
)
