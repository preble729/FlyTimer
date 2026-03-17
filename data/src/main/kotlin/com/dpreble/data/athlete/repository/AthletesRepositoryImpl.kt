package com.dpreble.data.athlete.repository

import com.dpreble.data.athlete.local.AthleteDao
import com.dpreble.data.athlete.local.AthleteEntity
import com.dpreble.data.times.local.TimeDao
import com.dpreble.domain.athlete.models.Athlete
import com.dpreble.domain.athlete.models.AthleteBestTime
import com.dpreble.domain.athlete.models.AthleteDetails
import com.dpreble.domain.athlete.models.AthleteRaceHistoryEntry
import com.dpreble.domain.athlete.repository.AthletesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class AthletesRepositoryImpl(
    private val athleteDao: AthleteDao,
    private val timeDao: TimeDao
) : AthletesRepository {
    override fun observeAthletes(): Flow<List<Athlete>> {
        return athleteDao.getAll().map { athletes ->
            athletes.map { athlete ->
                Athlete(
                    id = athlete.id,
                    name = athlete.name
                )
            }
        }
    }

    override fun observeAthleteDetails(athleteId: Long): Flow<AthleteDetails?> {
        return combine(
            athleteDao.observeById(athleteId),
            timeDao.observeBestTimesForAthlete(athleteId),
            timeDao.observeRaceHistoryForAthlete(athleteId)
        ) { athleteEntity, bestTimes, history ->
            athleteEntity?.let { athlete ->
                AthleteDetails(
                    athlete = Athlete(id = athlete.id, name = athlete.name),
                    bestTimes = bestTimes.map { row ->
                        AthleteBestTime(
                            raceName = row.raceName,
                            bestTimeMs = row.bestTimeMs
                        )
                    },
                    raceHistory = history.map { row ->
                        AthleteRaceHistoryEntry(
                            raceName = row.raceName,
                            submittedTimeMs = row.submittedTimeMs
                        )
                    }
                )
            }
        }
    }

    override suspend fun addAthlete(name: String): Long {
        return athleteDao.insert(AthleteEntity(name = name))
    }

    override suspend fun removeAthlete(id: Long) {
        athleteDao.deleteById(id)
    }
}
