package com.dpreble.domain.teams.repository

import com.dpreble.domain.teams.models.Team
import kotlinx.coroutines.flow.Flow

interface TeamsRepository {

    fun observeTeams(): Flow<List<Team>>

    suspend fun addTeam(name: String)

    suspend fun removeTeam(id: Int)
}