package com.dpreble.data.times.repository

import com.dpreble.data.times.local.TimeDao
import com.dpreble.data.times.local.TimeEntity
import com.dpreble.domain.race.models.RecentRace
import com.dpreble.domain.times.repository.TimesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimesRepositoryImpl(
    private val timeDao: TimeDao
) : TimesRepository {
    override suspend fun exportTime(
        athleteId: Long,
        raceId: Long,
        submittedTimeMs: Long
    ): Long {
        return timeDao.insert(
            TimeEntity(
                athleteId = athleteId,
                raceId = raceId,
                submittedTimeMs = submittedTimeMs
            )
        )
    }

    override fun observeRecentRaces(limit: Int): Flow<List<RecentRace>> {
        return timeDao.observeRecentRaces(limit).map { rows ->
            rows.map { row ->
                RecentRace(
                    athleteName = row.athleteName,
                    raceName = row.raceName,
                    submittedTimeMs = row.submittedTimeMs
                )
            }
        }
    }

    override fun observeTotalTimesCount(): Flow<Int> = timeDao.observeTotalTimesCount()

    override fun observeBestTimeMs(): Flow<Long?> = timeDao.observeBestTimeMs()
}
