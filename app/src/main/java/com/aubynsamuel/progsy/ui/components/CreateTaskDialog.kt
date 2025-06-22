package com.aubynsamuel.progsy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.aubynsamuel.progsy.domain.model.TaskCategory
import com.aubynsamuel.progsy.domain.model.TaskPriority
import com.aubynsamuel.progsy.domain.model.TaskRepetition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskDialog(
    onDismiss: () -> Unit,
    onCreate: (String, String?, TaskCategory, TaskPriority, TaskRepetition) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(TaskCategory.PERSONAL) }
    var selectedPriority by remember { mutableStateOf(TaskPriority.MEDIUM) }
    var selectedRepetition by remember { mutableStateOf(TaskRepetition.NONE) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Task") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                // Category Selection
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelMedium
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(TaskCategory.entries) { category ->
                        FilterChip(
                            onClick = { selectedCategory = category },
                            label = { Text(category.name.lowercase().capitalize()) },
                            selected = selectedCategory == category
                        )
                    }
                }

                // Priority Selection
                Text(
                    text = "Priority",
                    style = MaterialTheme.typography.labelMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TaskPriority.entries.forEach { priority ->
                        Row(
                            modifier = Modifier.selectable(
                                selected = selectedPriority == priority,
                                onClick = { selectedPriority = priority },
                                role = Role.RadioButton
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPriority == priority,
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = priority.name.lowercase().capitalize(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Repetition Selection
                Text(
                    text = "Repetition",
                    style = MaterialTheme.typography.labelMedium
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(TaskRepetition.values()) { repetition ->
                        FilterChip(
                            onClick = { selectedRepetition = repetition },
                            label = { Text(repetition.name.lowercase().capitalize()) },
                            selected = selectedRepetition == repetition
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank()) {
                        onCreate(
                            title,
                            description.takeIf { it.isNotBlank() },
                            selectedCategory,
                            selectedPriority,
                            selectedRepetition
                        )
                        onDismiss()
                    }
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}