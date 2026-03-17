package com.dpreble.data.race.repository

import com.dpreble.data.race.local.RaceDao
import com.dpreble.data.race.local.RaceEntity
import com.dpreble.domain.race.models.Race
import com.dpreble.domain.race.repository.RacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RacesRepositoryImpl(
    private val raceDao: RaceDao
) : RacesRepository {
    override fun observeRaces(): Flow<List<Race>> {
        return raceDao.getAll().map { races ->
            races.map { race ->
                Race(
                    id = race.id,
                    raceName = race.raceName
                )
            }
        }
    }

    override suspend fun addRace(name: String): Long {
        return raceDao.insert(RaceEntity(raceName = name))
    }

    override suspend fun removeRace(id: Long) {
        raceDao.deleteById(id)
    }
}
