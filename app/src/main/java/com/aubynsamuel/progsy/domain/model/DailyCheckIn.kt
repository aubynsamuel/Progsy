package com.aubynsamuel.progsy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime


@Entity(tableName = "daily_checkins")
data class DailyCheckIn(
    @PrimaryKey val date: LocalDate,
    val morningGoal1: String? = null,
    val morningGoal2: String? = null,
    val morningGoal3: String? = null,
    val morningEnergy: Int? = null, // 1-5
    val morningMood: Int? = null, // 1-5
    val sleepRating: Int? = null, // 1-5
    val eveningSummary: String? = null,
    val eveningMood: Int? = null, // 1-5
    val eveningProductivity: Int? = null, // 1-5
    val learningNote: String? = null,
    val journalEntry: String? = null,
    val goalsCompleted: Boolean? = null,
    val createdAt: LocalDateTime,
)
