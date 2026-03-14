package com.dpreble.flytimer.athletes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AthletesScreen(
    onAthleteClick: (Long) -> Unit,
    viewModel: AthletesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Athletes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        if (uiState.athletes.isEmpty()) {
            Text(
                text = "No athletes found. Add athletes in Settings.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.athletes, key = { it.id }) { athlete ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAthleteClick(athlete.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = athlete.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "View",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AthleteDetailScreen(
    viewModel: AthleteDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val details = uiState.details

    if (details == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Athlete not found")
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = details.athlete.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = "Best Times By Race",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (details.bestTimes.isEmpty()) {
            item {
                Text("No race times yet.")
            }
        } else {
            items(details.bestTimes, key = { it.raceName }) { bestTime ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(bestTime.raceName)
                    Text(formatTimer(bestTime.bestTimeMs))
                }
                HorizontalDivider()
            }
        }

        item {
            Text(
                text = "Previous Races (Chronological)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (details.raceHistory.isEmpty()) {
            item {
                Text("No previous races yet.")
            }
        } else {
            items(details.raceHistory.indices.toList(), key = { it }) { idx ->
                val entry = details.raceHistory[idx]
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(entry.raceName)
                    Text(formatTimer(entry.submittedTimeMs))
                }
                HorizontalDivider()
            }
        }
    }
}

private fun formatTimer(milliseconds: Long): String {
    val totalCentiseconds = milliseconds / 10
    val minutes = (totalCentiseconds / 6000) % 100
    val seconds = (totalCentiseconds / 100) % 60
    val centiseconds = totalCentiseconds % 100
    return "%02d:%02d:%02d".format(minutes, seconds, centiseconds)
}
