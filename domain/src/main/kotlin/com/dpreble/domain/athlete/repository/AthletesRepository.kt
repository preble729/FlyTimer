package com.dpreble.domain.athlete.repository

import com.dpreble.domain.athlete.models.Athlete
import com.dpreble.domain.athlete.models.AthleteDetails
import kotlinx.coroutines.flow.Flow

interface AthletesRepository {
    fun observeAthletes(): Flow<List<Athlete>>
    fun observeAthleteDetails(athleteId: Long): Flow<AthleteDetails?>
    suspend fun addAthlete(name: String): Long
    suspend fun removeAthlete(id: Long)
}