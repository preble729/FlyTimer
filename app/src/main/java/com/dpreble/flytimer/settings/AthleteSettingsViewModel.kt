package com.dpreble.flytimer.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpreble.domain.athlete.models.Athlete
import com.dpreble.domain.athlete.repository.AthletesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AthleteSettingsUiState(
    val athleteNameInput: String = "",
    val athletes: List<Athlete> = emptyList()
)

@HiltViewModel
class AthleteSettingsViewModel @Inject constructor(
    private val athletesRepository: AthletesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AthleteSettingsUiState())
    val uiState: StateFlow<AthleteSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            athletesRepository.observeAthletes().collect { athletes ->
                _uiState.update { it.copy(athletes = athletes) }
            }
        }
    }

    fun onAthleteNameInputChanged(value: String) {
        _uiState.update { it.copy(athleteNameInput = value) }
    }

    fun addAthlete() {
        val name = _uiState.value.athleteNameInput.trim()
        if (name.isEmpty()) return

        viewModelScope.launch {
            athletesRepository.addAthlete(name)
            _uiState.update { it.copy(athleteNameInput = "") }
        }
    }

    fun removeAthlete(id: Long) {
        viewModelScope.launch {
            athletesRepository.removeAthlete(id)
        }
    }
}
