package com.aubynsamuel.progsy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aubynsamuel.progsy.domain.model.DailyCheckIn

@Composable
fun MorningCheckIn(
    checkIn: DailyCheckIn,
    onSubmit: (String, String, String, Int, Int, Int) -> Unit,
) {
    var goal1 by remember { mutableStateOf(checkIn.morningGoal1 ?: "") }
    var goal2 by remember { mutableStateOf(checkIn.morningGoal2 ?: "") }
    var goal3 by remember { mutableStateOf(checkIn.morningGoal3 ?: "") }
    var energy by remember { mutableIntStateOf(checkIn.morningEnergy ?: 3) }
    var mood by remember { mutableIntStateOf(checkIn.morningMood ?: 3) }
    var sleepRating by remember { mutableIntStateOf(checkIn.sleepRating ?: 3) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "🌅 Good Morning!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Set your top 3 focus goals for today:",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = goal1,
                    onValueChange = { goal1 = it },
                    label = { Text("Goal 1") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = goal2,
                    onValueChange = { goal2 = it },
                    label = { Text("Goal 2") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = goal3,
                    onValueChange = { goal3 = it },
                    label = { Text("Goal 3") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "How are you feeling?",
                    style = MaterialTheme.typography.titleMedium
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Energy Level:")
                        RatingBar(
                            rating = energy,
                            onRatingChange = { energy = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Mood:")
                        RatingBar(
                            rating = mood,
                            onRatingChange = { mood = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Sleep Quality:")
                        RatingBar(
                            rating = sleepRating,
                            onRatingChange = { sleepRating = it }
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                onSubmit(goal1, goal2, goal3, energy, mood, sleepRating)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Morning Check-In")
        }
    }
}