package com.dpreble.flytimer.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AthleteSettingsPage(
    viewModel: AthleteSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsListPage(
        title = "Athletes",
        inputLabel = "Athlete Name",
        inputValue = uiState.athleteNameInput,
        onInputChange = viewModel::onAthleteNameInputChanged,
        onAddClicked = viewModel::addAthlete,
        rows = uiState.athletes.map { athlete ->
            SettingsRow(
                id = athlete.id,
                primaryText = athlete.name
            )
        },
        onRemoveClicked = viewModel::removeAthlete
    )
}