package com.dpreble.flytimer.timers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpreble.domain.model.Athlete
import com.dpreble.domain.model.Race
import com.dpreble.domain.repository.AthletesRepository
import com.dpreble.domain.repository.RacesRepository
import com.dpreble.domain.repository.TimesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TimerScreenUiState(
    val athletes: List<Athlete> = emptyList(),
    val races: List<Race> = emptyList(),
    val selectedAthleteId: Long? = null,
    val selectedRaceId: Long? = null,
    val athleteExpanded: Boolean = false,
    val raceExpanded: Boolean = false,
    val isRunning: Boolean = false,
    val elapsedMs: Long = 0L
)

sealed interface TimerScreenIntent {
    data class SetAthleteExpanded(val expanded: Boolean) : TimerScreenIntent
    data class SetRaceExpanded(val expanded: Boolean) : TimerScreenIntent
    data class SelectAthlete(val athleteId: Long) : TimerScreenIntent
    data class SelectRace(val raceId: Long) : TimerScreenIntent
    data object ToggleTimer : TimerScreenIntent
    data object ResetTimer : TimerScreenIntent
    data object ExportTime : TimerScreenIntent
}

@HiltViewModel
class TimerScreenViewModel @Inject constructor(
    private val athletesRepository: AthletesRepository,
    private val racesRepository: RacesRepository,
    private val timesRepository: TimesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimerScreenUiState())
    val uiState: StateFlow<TimerScreenUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            athletesRepository.observeAthletes().collect { athletes ->
                _uiState.update { state ->
                    state.copy(
                        athletes = athletes,
                        selectedAthleteId = state.selectedAthleteId?.takeIf { selectedId ->
                            athletes.any { it.id == selectedId }
                        }
                    )
                }
            }
        }

        viewModelScope.launch {
            racesRepository.observeRaces().collect { races ->
                _uiState.update { state ->
                    state.copy(
                        races = races,
                        selectedRaceId = state.selectedRaceId?.takeIf { selectedId ->
                            races.any { it.id == selectedId }
                        }
                    )
                }
            }
        }
    }

    fun onIntent(intent: TimerScreenIntent) {
        when (intent) {
            TimerScreenIntent.ToggleTimer -> {
                if (!canToggleTimer(_uiState.value)) return
                val wasRunning = _uiState.value.isRunning
                _uiState.update { reduce(it, intent) }
                if (wasRunning) stopTimer() else startTimer()
            }
            TimerScreenIntent.ResetTimer -> {
                stopTimer()
                _uiState.update { it.copy(elapsedMs = 0L) }
            }
            TimerScreenIntent.ExportTime -> {
                exportCurrentTime()
            }
            else -> {
                _uiState.update { reduce(it, intent) }
            }
        }
    }

    private fun reduce(state: TimerScreenUiState, intent: TimerScreenIntent): TimerScreenUiState {
        return when (intent) {
            is TimerScreenIntent.SetAthleteExpanded -> state.copy(athleteExpanded = intent.expanded)
            is TimerScreenIntent.SetRaceExpanded -> state.copy(raceExpanded = intent.expanded)
            is TimerScreenIntent.SelectAthlete -> state.copy(
                selectedAthleteId = intent.athleteId,
                athleteExpanded = false
            )
            is TimerScreenIntent.SelectRace -> state.copy(
                selectedRaceId = intent.raceId,
                raceExpanded = false
            )
            TimerScreenIntent.ToggleTimer -> state.copy(isRunning = !state.isRunning)
            TimerScreenIntent.ResetTimer -> state
            TimerScreenIntent.ExportTime -> state
        }
    }

    private fun canToggleTimer(state: TimerScreenUiState): Boolean {
        return state.isRunning || (state.selectedAthleteId != null && state.selectedRaceId != null)
    }

    private fun exportCurrentTime() {
        val state = _uiState.value
        val athleteId = state.selectedAthleteId ?: return
        val raceId = state.selectedRaceId ?: return
        if (state.elapsedMs <= 0L) return

        viewModelScope.launch {
            timesRepository.exportTime(
                athleteId = athleteId,
                raceId = raceId,
                submittedTimeMs = state.elapsedMs
            )
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.isRunning) {
                delay(10L)
                _uiState.update { state -> state.copy(elapsedMs = state.elapsedMs + 10L) }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update { it.copy(isRunning = false) }
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}
