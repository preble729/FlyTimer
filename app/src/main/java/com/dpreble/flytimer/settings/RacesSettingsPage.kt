package com.dpreble.flytimer.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RacesSettingsPage(
    viewModel: RacesSettingsPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsListPage(
        title = "Races",
        inputLabel = "Race Name",
        inputValue = uiState.raceNameInput,
        onInputChange = viewModel::onRaceNameInputChanged,
        onAddClicked = viewModel::addRace,
        rows = uiState.races.map { race ->
            SettingsRow(
                id = race.id,
                primaryText = race.raceName
            )
        },
        onRemoveClicked = viewModel::removeRace
    )
}