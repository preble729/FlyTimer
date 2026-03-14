package com.dpreble.domain.repository

import com.dpreble.domain.model.Athlete
import com.dpreble.domain.model.AthleteDetails
import kotlinx.coroutines.flow.Flow

interface AthletesRepository {
    fun observeAthletes(): Flow<List<Athlete>>
    fun observeAthleteDetails(athleteId: Long): Flow<AthleteDetails?>
    suspend fun addAthlete(name: String): Long
    suspend fun removeAthlete(id: Long)
}
