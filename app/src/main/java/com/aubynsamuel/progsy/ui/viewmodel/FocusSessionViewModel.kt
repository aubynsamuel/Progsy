package com.aubynsamuel.progsy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.progsy.data.local.FocusSessionDao
import com.aubynsamuel.progsy.domain.model.FocusSession
import com.aubynsamuel.progsy.domain.model.TaskCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FocusSessionViewModel @Inject constructor(
    private val focusSessionDao: FocusSessionDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FocusSessionUiState())
    val uiState: StateFlow<FocusSessionUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var currentSessionId: String? = null

    fun startFocusSession(durationMinutes: Int, category: TaskCategory?) {
        val sessionId = UUID.randomUUID().toString()
        currentSessionId = sessionId

        val session = FocusSession(
            id = sessionId,
            category = category,
            durationMinutes = durationMinutes,
            startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )

        viewModelScope.launch {
            focusSessionDao.insertSession(session)
        }

        _uiState.value = _uiState.value.copy(
            isSessionActive = true,
            totalDurationMinutes = durationMinutes,
            remainingSeconds = durationMinutes * 60,
            currentCategory = category
        )

        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingSeconds > 0 && _uiState.value.isSessionActive) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    remainingSeconds = _uiState.value.remainingSeconds - 1
                )
            }

            if (_uiState.value.remainingSeconds <= 0) {
                completeSession()
            }
        }
    }

    fun pauseSession() {
        timerJob?.cancel()
        _uiState.value = _uiState.value.copy(isPaused = true)
    }

    fun resumeSession() {
        _uiState.value = _uiState.value.copy(isPaused = false)
        startTimer()
    }

    fun stopSession() {
        timerJob?.cancel()
        completeSession()
    }

    private fun completeSession() {
        val actualDuration =
            _uiState.value.totalDurationMinutes - (_uiState.value.remainingSeconds / 60)

        viewModelScope.launch {
            currentSessionId?.let { sessionId ->
                val session = focusSessionDao.getSessionById(sessionId)
                session?.let {
                    val updatedSession = it.copy(
                        actualDurationMinutes = actualDuration,
                        completed = true,
                        endTime = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                    focusSessionDao.updateSession(updatedSession)
                }
            }
        }

        _uiState.value = _uiState.value.copy(
            isSessionActive = false,
            isPaused = false,
            showCompletionDialog = true
        )
    }

    fun dismissCompletionDialog() {
        _uiState.value = _uiState.value.copy(
            showCompletionDialog = false,
            remainingSeconds = 0,
            totalDurationMinutes = 0,
            currentCategory = null
        )
        currentSessionId = null
    }

    fun saveFocusRating(rating: Int, notes: String) {
        viewModelScope.launch {
            currentSessionId?.let { sessionId ->
                val session = focusSessionDao.getSessionById(sessionId)
                session?.let {
                    val updatedSession = it.copy(
                        focusRating = rating,
                        notes = notes.takeIf { notes.isNotBlank() }
                    )
                    focusSessionDao.updateSession(updatedSession)
                }
            }
        }
        dismissCompletionDialog()
    }
}

data class FocusSessionUiState(
    val isSessionActive: Boolean = false,
    val isPaused: Boolean = false,
    val totalDurationMinutes: Int = 0,
    val remainingSeconds: Int = 0,
    val currentCategory: TaskCategory? = null,
    val showCompletionDialog: Boolean = false,
)