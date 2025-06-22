package com.aubynsamuel.progsy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aubynsamuel.progsy.domain.model.DailyCheckIn

@Composable
fun EveningReflection(
    checkIn: DailyCheckIn,
    onSubmit: (String, Int, Int, String, String, Boolean) -> Unit,
) {
    var summary by remember { mutableStateOf(checkIn.eveningSummary ?: "") }
    var mood by remember { mutableStateOf(checkIn.eveningMood ?: 3) }
    var productivity by remember { mutableStateOf(checkIn.eveningProductivity ?: 3) }
    var learning by remember { mutableStateOf(checkIn.learningNote ?: "") }
    var journal by remember { mutableStateOf(checkIn.journalEntry ?: "") }
    var goalsCompleted by remember { mutableStateOf(checkIn.goalsCompleted ?: false) }

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
                    text = "🌙 Evening Reflection",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = summary,
                    onValueChange = { summary = it },
                    label = { Text("What did you accomplish today?") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = goalsCompleted,
                        onCheckedChange = { goalsCompleted = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("I completed my morning goals")
                }
            }
        }

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "How was your day?",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Evening Mood:")
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
                    Text("Productivity:")
                    RatingBar(
                        rating = productivity,
                        onRatingChange = { productivity = it }
                    )
                }
            }
        }

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = learning,
                    onValueChange = { learning = it },
                    label = { Text("What did you learn today?") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )

                OutlinedTextField(
                    value = journal,
                    onValueChange = { journal = it },
                    label = { Text("Journal Entry (one-liner)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
            }
        }

        Button(
            onClick = {
                onSubmit(summary, mood, productivity, learning, journal, goalsCompleted)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Evening Reflection")
        }
    }
}