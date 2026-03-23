package com.dpreble.di

import com.dpreble.data.athlete.local.AthleteDao
import com.dpreble.data.athlete.repository.AthletesRepositoryImpl
import com.dpreble.data.race.local.RaceDao
import com.dpreble.data.race.repository.RacesRepositoryImpl
import com.dpreble.data.teams.local.TeamDao
import com.dpreble.data.teams.repository.TeamsRepositoryImpl
import com.dpreble.data.times.local.TimeDao
import com.dpreble.data.times.repository.TimesRepositoryImpl
import com.dpreble.domain.athlete.repository.AthletesRepository
import com.dpreble.domain.race.repository.RacesRepository
import com.dpreble.domain.teams.repository.TeamsRepository
import com.dpreble.domain.times.repository.TimesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAthletesRepository(
        athleteDao: AthleteDao,
        timeDao: TimeDao
    ): AthletesRepository {
        return AthletesRepositoryImpl(athleteDao, timeDao)
    }

    @Provides
    @Singleton
    fun provideRacesRepository(raceDao: RaceDao): RacesRepository {
        return RacesRepositoryImpl(raceDao)
    }

    @Provides
    @Singleton
    fun provideTimesRepository(timeDao: TimeDao): TimesRepository {
        return TimesRepositoryImpl(timeDao)
    }

    @Provides
    @Singleton
    fun provideTeamsRepository(teamDao: TeamDao): TeamsRepository {
        return TeamsRepositoryImpl(teamDao)
    }
}
