package com.dpreble.flytimer.athletes

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

data class AthletesUiState(
    val athletes: List<Athlete> = emptyList()
)

@HiltViewModel
class AthletesViewModel @Inject constructor(
    private val athletesRepository: AthletesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AthletesUiState())
    val uiState: StateFlow<AthletesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            athletesRepository.observeAthletes().collect { athletes ->
                _uiState.update { it.copy(athletes = athletes) }
            }
        }
    }
}
