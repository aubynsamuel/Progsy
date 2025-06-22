package com.aubynsamuel.progsy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.progsy.domain.model.Task
import com.aubynsamuel.progsy.domain.model.TaskCategory
import com.aubynsamuel.progsy.domain.model.TaskPriority
import com.aubynsamuel.progsy.domain.model.TaskRepetition
import com.aubynsamuel.progsy.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    val tasks = taskRepository.getAllTasks()

    fun toggleTaskCompletion(taskId: String, completed: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTaskCompletion(taskId, completed)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    @OptIn(ExperimentalTime::class)
    fun createTask(
        title: String,
        description: String?,
        category: TaskCategory,
        priority: TaskPriority,
        repetition: TaskRepetition,
    ) {
        viewModelScope.launch {
            val task = Task(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                category = category,
                priority = priority,
                repetition = repetition,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
            taskRepository.insertTask(task)
        }
    }

    fun showCreateDialog() {
        _uiState.value = _uiState.value.copy(showCreateDialog = true)
    }

    fun hideCreateDialog() {
        _uiState.value = _uiState.value.copy(showCreateDialog = false)
    }
}

data class TaskUiState(
    val showCreateDialog: Boolean = false,
    val isLoading: Boolean = false,
)
