package com.dpreble.flytimer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpreble.domain.model.RecentRace
import com.dpreble.domain.repository.AthletesRepository
import com.dpreble.domain.repository.RacesRepository
import com.dpreble.domain.repository.TimesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class HomeUiState(
    val athleteCount: Int = 0,
    val raceCount: Int = 0,
    val recordedTimesCount: Int = 0,
    val bestTimeMs: Long? = null,
    val recentRaces: List<RecentRace> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    athletesRepository: AthletesRepository,
    racesRepository: RacesRepository,
    timesRepository: TimesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                athletesRepository.observeAthletes(),
                racesRepository.observeRaces(),
                timesRepository.observeTotalTimesCount(),
                timesRepository.observeBestTimeMs(),
                timesRepository.observeRecentRaces(limit = 10)
            ) { athletes, races, totalTimes, bestTime, recentRaces ->
                HomeUiState(
                    athleteCount = athletes.size,
                    raceCount = races.size,
                    recordedTimesCount = totalTimes,
                    bestTimeMs = bestTime,
                    recentRaces = recentRaces
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
