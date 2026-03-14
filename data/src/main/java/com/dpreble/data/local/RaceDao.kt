package com.dpreble.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {
    @Query("SELECT * FROM races ORDER BY race_name ASC")
    fun getAll(): Flow<List<RaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(race: RaceEntity): Long

    @Query("DELETE FROM races WHERE id = :raceId")
    suspend fun deleteById(raceId: Long)
}
