package com.dpreble.flytimer.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

private enum class SettingsTab(val title: String) {
    Athletes("Athletes"),
    Races("Races")
}

@Composable
fun SettingsScreen() {
    var selectedTab by remember { mutableStateOf(SettingsTab.Athletes) }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab.ordinal) {
            SettingsTab.entries.forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    text = { Text(tab.title) }
                )
            }
        }

        when (selectedTab) {
            SettingsTab.Athletes -> AthleteSettingsPage()
            SettingsTab.Races -> RacesSettingsPage()
        }
    }
}

@Composable
private fun AthleteSettingsPage(
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

@Composable
private fun RacesSettingsPage(
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

private data class SettingsRow(
    val id: Long,
    val primaryText: String
)

@Composable
private fun SettingsListPage(
    title: String,
    inputLabel: String,
    inputValue: String,
    onInputChange: (String) -> Unit,
    onAddClicked: () -> Unit,
    rows: List<SettingsRow>,
    onRemoveClicked: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputValue,
                onValueChange = onInputChange,
                label = { Text(inputLabel) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Button(onClick = onAddClicked) {
                Text("Add")
            }
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rows, key = { it.id }) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(row.primaryText)
                    Button(onClick = { onRemoveClicked(row.id) }) {
                        Text("Remove")
                    }
                }
                HorizontalDivider()
            }
        }
    }
}
