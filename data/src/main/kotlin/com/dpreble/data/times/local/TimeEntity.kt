package com.dpreble.data.times.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dpreble.data.athlete.local.AthleteEntity
import com.dpreble.data.race.local.RaceEntity

@Entity(
    tableName = "times",
    foreignKeys = [
        ForeignKey(
            entity = AthleteEntity::class,
            parentColumns = ["id"],
            childColumns = ["athlete_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["race_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["athlete_id"]),
        Index(value = ["race_id"])
    ]
)
data class TimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "athlete_id")
    val athleteId: Long,
    @ColumnInfo(name = "race_id")
    val raceId: Long,
    @ColumnInfo(name = "submitted_time_ms")
    val submittedTimeMs: Long
)
