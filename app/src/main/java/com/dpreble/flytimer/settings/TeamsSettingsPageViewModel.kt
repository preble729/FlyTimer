package com.dpreble.flytimer.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpreble.domain.teams.models.Team
import com.dpreble.domain.teams.repository.TeamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TeamsSettingsUiState(
    val teamNameInput: String = "",
    val teams: List<Team> = emptyList()
)

@HiltViewModel
class TeamsSettingsPageViewModel @Inject constructor(
    private val teamsRepository: TeamsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TeamsSettingsUiState())
    val uiState: StateFlow<TeamsSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            teamsRepository.observeTeams().collect { teams ->
                _uiState.update { it.copy(teams = teams) }
            }
        }
    }

    fun onTeamNameInputChange(value: String) {
        _uiState.update { it.copy(teamNameInput = value) }
    }

    fun onAddTeamClick() {
        val name = uiState.value.teamNameInput.trim()
        if (name.isEmpty()) return

        viewModelScope.launch {
            teamsRepository.addTeam(name)
            _uiState.update { it.copy(teamNameInput = "") }
        }
    }

    fun onRemoveTeamClick(id: Long) {
        viewModelScope.launch {
            teamsRepository.removeTeam(id.toInt())
        }
    }
}