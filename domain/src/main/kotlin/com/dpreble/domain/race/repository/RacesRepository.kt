package com.dpreble.domain.race.repository

import com.dpreble.domain.race.models.Race
import kotlinx.coroutines.flow.Flow

interface RacesRepository {
    fun observeRaces(): Flow<List<Race>>
    suspend fun addRace(name: String, lapsPerAthlete: Int): Long
    suspend fun removeRace(id: Long)
}