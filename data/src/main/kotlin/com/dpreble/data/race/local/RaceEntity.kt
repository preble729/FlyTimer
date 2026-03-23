package com.dpreble.data.race.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "races")
data class RaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "race_name")
    val raceName: String,
    @ColumnInfo(name = "laps_per_athlete")
    val lapsPerAthlete: Int
)
