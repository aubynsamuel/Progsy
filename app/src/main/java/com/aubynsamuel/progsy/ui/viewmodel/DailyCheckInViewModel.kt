package com.aubynsamuel.progsy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.progsy.data.local.DailyCheckInDao
import com.aubynsamuel.progsy.domain.model.DailyCheckIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime


@HiltViewModel
class DailyCheckInViewModel @Inject constructor(
    private val dailyCheckInDao: DailyCheckInDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyCheckInUiState())
    val uiState: StateFlow<DailyCheckInUiState> = _uiState.asStateFlow()

    init {
        loadTodayCheckIn()
    }

    @OptIn(ExperimentalTime::class)
    private fun loadTodayCheckIn() {
        viewModelScope.launch {
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val checkIn = dailyCheckInDao.getCheckInByDate(today)
            _uiState.value = _uiState.value.copy(
                currentCheckIn = checkIn ?: DailyCheckIn(
                    date = today,
                    createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
            )
        }
    }

    fun updateMorningCheckIn(
        goal1: String,
        goal2: String,
        goal3: String,
        energy: Int,
        mood: Int,
        sleepRating: Int,
    ) {
        viewModelScope.launch {
            val updated = _uiState.value.currentCheckIn.copy(
                morningGoal1 = goal1.takeIf { it.isNotBlank() },
                morningGoal2 = goal2.takeIf { it.isNotBlank() },
                morningGoal3 = goal3.takeIf { it.isNotBlank() },
                morningEnergy = energy,
                morningMood = mood,
                sleepRating = sleepRating
            )
            dailyCheckInDao.insertCheckIn(updated)
            _uiState.value = _uiState.value.copy(currentCheckIn = updated)
        }
    }

    fun updateEveningReflection(
        summary: String,
        mood: Int,
        productivity: Int,
        learning: String,
        journal: String,
        goalsCompleted: Boolean,
    ) {
        viewModelScope.launch {
            val updated = _uiState.value.currentCheckIn.copy(
                eveningSummary = summary.takeIf { it.isNotBlank() },
                eveningMood = mood,
                eveningProductivity = productivity,
                learningNote = learning.takeIf { it.isNotBlank() },
                journalEntry = journal.takeIf { it.isNotBlank() },
                goalsCompleted = goalsCompleted
            )
            dailyCheckInDao.insertCheckIn(updated)
            _uiState.value = _uiState.value.copy(currentCheckIn = updated)
        }
    }
}

data class DailyCheckInUiState @OptIn(ExperimentalTime::class) constructor(
    val currentCheckIn: DailyCheckIn = DailyCheckIn(
        date = LocalDate.fromEpochDays(0),
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    val isLoading: Boolean = false,
)