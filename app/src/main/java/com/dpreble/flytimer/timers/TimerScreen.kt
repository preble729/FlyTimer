package com.dpreble.flytimer.timers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dpreble.domain.athlete.models.Athlete
import com.dpreble.domain.race.models.Race
import com.dpreble.flytimer.ui.theme.FlyTimerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(viewModel: TimerScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    TimerScreenContent(
        uiState = uiState,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreenContent(
    uiState: TimerScreenUiState,
    onIntent: (TimerScreenIntent) -> Unit
) {
    val selectedAthleteName = uiState.athletes
        .firstOrNull { it.id == uiState.selectedAthleteId }
        ?.name
        .orEmpty()
    val selectedRaceName = uiState.races
        .firstOrNull { it.id == uiState.selectedRaceId }
        ?.raceName
        .orEmpty()

    val canStart = uiState.selectedAthleteId != null && uiState.selectedRaceId != null
    val isToggleEnabled = uiState.isRunning || canStart
    val canExport = canStart && uiState.elapsedMs > 0L

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Race Timer",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        ExposedDropdownMenuBox(
            expanded = uiState.athleteExpanded,
            onExpandedChange = {
                onIntent(TimerScreenIntent.SetAthleteExpanded(!uiState.athleteExpanded))
            }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(
                        type = MenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    )
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedAthleteName,
                onValueChange = {},
                label = { Text("Athlete") },
                placeholder = { Text("Select Athlete") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.athleteExpanded)
                }
            )

            DropdownMenu(
                expanded = uiState.athleteExpanded,
                onDismissRequest = { onIntent(TimerScreenIntent.SetAthleteExpanded(false)) }
            ) {
                uiState.athletes.forEach { athlete ->
                    DropdownMenuItem(
                        text = { Text(athlete.name) },
                        onClick = {
                            onIntent(TimerScreenIntent.SelectAthlete(athlete.id))
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = uiState.raceExpanded,
            onExpandedChange = {
                onIntent(TimerScreenIntent.SetRaceExpanded(!uiState.raceExpanded))
            }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(
                        type = MenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    )
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedRaceName,
                onValueChange = {},
                label = { Text("Race") },
                placeholder = { Text("Select Race") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.raceExpanded)
                }
            )

            DropdownMenu(
                expanded = uiState.raceExpanded,
                onDismissRequest = { onIntent(TimerScreenIntent.SetRaceExpanded(false)) }
            ) {
                uiState.races.forEach { race ->
                    DropdownMenuItem(
                        text = { Text(race.raceName) },
                        onClick = {
                            onIntent(TimerScreenIntent.SelectRace(race.id))
                        }
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                EightSegmentTimerDisplay(text = formatTimer(uiState.elapsedMs))

                Button(
                    onClick = { onIntent(TimerScreenIntent.ToggleTimer) },
                    enabled = isToggleEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.isRunning) Color(0xFFC62828) else Color(0xFF2E7D32),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF9E9E9E),
                        disabledContentColor = Color(0xFFE0E0E0)
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = if (uiState.isRunning) "Stop" else "Start",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onIntent(TimerScreenIntent.ResetTimer) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF9A825),
                            contentColor = Color(0xFF1A1A1A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Reset",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { onIntent(TimerScreenIntent.ExportTime) },
                        enabled = canExport,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1565C0),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFF90CAF9),
                            disabledContentColor = Color(0xFFE3F2FD)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Export",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EightSegmentTimerDisplay(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        text.take(8).forEach { char ->
            Box(
                modifier = Modifier
                    .size(width = 34.dp, height = 52.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHighest,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = char.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
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

@Preview(showBackground = true)
@Composable
private fun TimerScreenContentPreview() {
    FlyTimerTheme {
        TimerScreenContent(
            uiState = TimerScreenUiState(
                athletes = listOf(Athlete(id = 1, name = "Jordan Wells")),
                races = listOf(Race(id = 1, lapsPerAthlete = 1, raceName = "20m Fly")),
                selectedAthleteId = 1,
                selectedRaceId = 1,
                isRunning = true,
                elapsedMs = 18_430L
            ),
            onIntent = {}
        )
    }
}
