package com.aubynsamuel.progsy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String,
    val title: String,
    val description: String? = null,
    val category: TaskCategory,
    val priority: TaskPriority,
    val dueTime: LocalTime? = null,
    val repetition: TaskRepetition,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime,
    val completedAt: LocalDateTime? = null,
)

enum class TaskCategory {
    STUDY, WORK, FITNESS, FAITH, HEALTH, PERSONAL, OTHER
}

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}

enum class TaskRepetition {
    NONE, DAILY, WEEKLY, CUSTOM
}
