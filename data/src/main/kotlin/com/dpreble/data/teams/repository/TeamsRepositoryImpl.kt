package com.dpreble.data.teams.repository

import com.dpreble.data.teams.local.TeamDao
import com.dpreble.data.teams.local.TeamEntity
import com.dpreble.domain.teams.models.Team
import com.dpreble.domain.teams.repository.TeamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TeamsRepositoryImpl(
    private val teamDao: TeamDao
) : TeamsRepository {
    override fun observeTeams(): Flow<List<Team>> {
        return teamDao.getAll().map { teams ->
            teams.map { team ->
                Team(
                    id = team.id,
                    name = team.teamName
                )
            }
        }
    }

    override suspend fun addTeam(name: String) {
        teamDao.insert(TeamEntity(teamName = name))
    }

    override suspend fun removeTeam(id: Int) {
        teamDao.deleteById(id)
    }
}