package com.dpreble.di

import com.dpreble.data.local.AthleteDao
import com.dpreble.data.local.RaceDao
import com.dpreble.data.local.TimeDao
import com.dpreble.data.repository.AthletesRepositoryImpl
import com.dpreble.data.repository.RacesRepositoryImpl
import com.dpreble.data.repository.TimesRepositoryImpl
import com.dpreble.domain.repository.AthletesRepository
import com.dpreble.domain.repository.RacesRepository
import com.dpreble.domain.repository.TimesRepository
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
}
