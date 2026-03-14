package com.dpreble.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {
    @Insert
    suspend fun insert(time: TimeEntity): Long

    @Query(
        """
        SELECT r.race_name AS raceName, MIN(t.submitted_time_ms) AS bestTimeMs
        FROM times t
        INNER JOIN races r ON r.id = t.race_id
        WHERE t.athlete_id = :athleteId
        GROUP BY t.race_id, r.race_name
        ORDER BY r.race_name ASC
        """
    )
    fun observeBestTimesForAthlete(athleteId: Long): Flow<List<AthleteBestTimeRow>>

    @Query(
        """
        SELECT r.race_name AS raceName, t.submitted_time_ms AS submittedTimeMs
        FROM times t
        INNER JOIN races r ON r.id = t.race_id
        WHERE t.athlete_id = :athleteId
        ORDER BY t.id ASC
        """
    )
    fun observeRaceHistoryForAthlete(athleteId: Long): Flow<List<AthleteRaceHistoryRow>>

    @Query(
        """
        SELECT a.name AS athleteName, r.race_name AS raceName, t.submitted_time_ms AS submittedTimeMs
        FROM times t
        INNER JOIN athletes a ON a.id = t.athlete_id
        INNER JOIN races r ON r.id = t.race_id
        ORDER BY t.id DESC
        LIMIT :limit
        """
    )
    fun observeRecentRaces(limit: Int): Flow<List<RecentRaceRow>>

    @Query("SELECT COUNT(*) FROM times")
    fun observeTotalTimesCount(): Flow<Int>

    @Query("SELECT MIN(submitted_time_ms) FROM times")
    fun observeBestTimeMs(): Flow<Long?>
}
