package com.aubynsamuel.progsy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "focus_sessions")
data class FocusSession(
    @PrimaryKey val id: String,
    val taskId: String? = null,
    val category: TaskCategory? = null,
    val durationMinutes: Int,
    val actualDurationMinutes: Int? = null,
    val completed: Boolean = false,
    val focusRating: Int? = null, // 1-5
    val notes: String? = null,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime? = null,
)
