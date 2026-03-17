package com.dpreble.data.athlete.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AthleteDao {
    @Query("SELECT * FROM athletes ORDER BY name ASC")
    fun getAll(): Flow<List<AthleteEntity>>

    @Query("SELECT * FROM athletes WHERE id = :athleteId LIMIT 1")
    fun observeById(athleteId: Long): Flow<AthleteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(athlete: AthleteEntity): Long

    @Query("DELETE FROM athletes WHERE id = :athleteId")
    suspend fun deleteById(athleteId: Long)
}
