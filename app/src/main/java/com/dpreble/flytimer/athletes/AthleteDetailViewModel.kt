package com.dpreble.flytimer.athletes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpreble.domain.athlete.models.AthleteDetails
import com.dpreble.domain.athlete.repository.AthletesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AthleteDetailUiState(
    val details: AthleteDetails? = null
)

@HiltViewModel
class AthleteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val athletesRepository: AthletesRepository
) : ViewModel() {
    private val athleteId: Long = checkNotNull(savedStateHandle["athleteId"])

    private val _uiState = MutableStateFlow(AthleteDetailUiState())
    val uiState: StateFlow<AthleteDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            athletesRepository.observeAthleteDetails(athleteId).collect { details ->
                _uiState.update { it.copy(details = details) }
            }
        }
    }
}
