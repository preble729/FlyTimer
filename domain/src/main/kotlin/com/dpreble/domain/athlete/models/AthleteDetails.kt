package com.dpreble.domain.athlete.models

import com.dpreble.domain.athlete.models.AthleteRaceHistoryEntry

data class AthleteDetails(
    val athlete: Athlete,
    val bestTimes: List<AthleteBestTime>,
    val raceHistory: List<AthleteRaceHistoryEntry>
)