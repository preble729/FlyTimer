package com.dpreble.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dpreble.data.athlete.local.AthleteDao
import com.dpreble.data.athlete.local.AthleteEntity
import com.dpreble.data.race.local.RaceDao
import com.dpreble.data.race.local.RaceEntity
import com.dpreble.data.times.local.TimeDao
import com.dpreble.data.times.local.TimeEntity

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
