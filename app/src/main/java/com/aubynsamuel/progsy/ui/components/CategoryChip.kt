package com.aubynsamuel.progsy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aubynsamuel.progsy.domain.model.TaskCategory

@Composable
fun CategoryChip(category: TaskCategory) {
    val color = when (category) {
        TaskCategory.STUDY -> Color(0xFF2196F3)
        TaskCategory.WORK -> Color(0xFF4CAF50)
        TaskCategory.FITNESS -> Color(0xFFFF9800)
        TaskCategory.FAITH -> Color(0xFF9C27B0)
        TaskCategory.HEALTH -> Color(0xFFE91E63)
        TaskCategory.PERSONAL -> Color(0xFF607D8B)
        TaskCategory.OTHER -> Color(0xFF795548)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = category.name.lowercase().capitalize(),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}