package com.dpreble.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        AthleteEntity::class,
        RaceEntity::class,
        TimeEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class FlyTimerDatabase : RoomDatabase() {
    abstract fun athleteDao(): AthleteDao
    abstract fun raceDao(): RaceDao
    abstract fun timeDao(): TimeDao
}
