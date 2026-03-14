package com.dpreble.domain.repository

import com.dpreble.domain.model.RecentRace
import kotlinx.coroutines.flow.Flow

interface TimesRepository {
    suspend fun exportTime(
        athleteId: Long,
        raceId: Long,
        submittedTimeMs: Long
    ): Long

    fun observeRecentRaces(limit: Int = 10): Flow<List<RecentRace>>
    fun observeTotalTimesCount(): Flow<Int>
    fun observeBestTimeMs(): Flow<Long?>
}
