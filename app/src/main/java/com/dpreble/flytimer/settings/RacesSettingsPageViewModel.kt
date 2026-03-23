package com.dpreble.flytimer.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpreble.domain.race.models.Race
import com.dpreble.domain.race.repository.RacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RacesSettingsUiState(
    val raceNameInput: String = "",
    val races: List<Race> = emptyList()
)

@HiltViewModel
class RacesSettingsPageViewModel @Inject constructor(
    private val racesRepository: RacesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RacesSettingsUiState())
    val uiState: StateFlow<RacesSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            racesRepository.observeRaces().collect { races ->
                _uiState.update { it.copy(races = races) }
            }
        }
    }

    fun onRaceNameInputChanged(value: String) {
        _uiState.update { it.copy(raceNameInput = value) }
    }

    fun addRace() {
        val name = _uiState.value.raceNameInput.trim()
        if (name.isEmpty()) return

        viewModelScope.launch {
            racesRepository.addRace(name, 1)
            _uiState.update { it.copy(raceNameInput = "") }
        }
    }

    fun removeRace(id: Long) {
        viewModelScope.launch {
            racesRepository.removeRace(id)
        }
    }
}
