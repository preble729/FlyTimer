package com.dpreble.di

import android.content.Context
import androidx.room.Room
import com.dpreble.data.FlyTimerDatabase
import com.dpreble.data.athlete.local.AthleteDao
import com.dpreble.data.race.local.RaceDao
import com.dpreble.data.times.local.TimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideFlyTimerDatabase(@ApplicationContext context: Context): FlyTimerDatabase {
        return Room.databaseBuilder(
            context,
            FlyTimerDatabase::class.java,
            "flytimer.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAthleteDao(database: FlyTimerDatabase): AthleteDao = database.athleteDao()

    @Provides
    fun provideRaceDao(database: FlyTimerDatabase): RaceDao = database.raceDao()

    @Provides
    fun provideTimeDao(database: FlyTimerDatabase): TimeDao = database.timeDao()
}
