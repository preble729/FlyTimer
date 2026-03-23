package com.dpreble.flytimer.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeamsSettingsPage(viewModel: TeamsSettingsPageViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    SettingsListPage(
        title = "Teams",
        inputLabel = "Team Name",
        inputValue = uiState.value.teamNameInput,
        onInputChange = viewModel::onTeamNameInputChange,
        onAddClicked = viewModel::onAddTeamClick,
        rows = uiState.value.teams.map { team ->
            SettingsRow(
                id = team.id.toLong(),
                primaryText = team.name
            )
        },
        onRemoveClicked = viewModel::onRemoveTeamClick
    )
}