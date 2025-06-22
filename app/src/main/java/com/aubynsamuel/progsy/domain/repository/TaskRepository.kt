package com.aubynsamuel.progsy.domain.repository

import com.aubynsamuel.progsy.domain.model.Task
import com.aubynsamuel.progsy.domain.model.TaskCategory
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByCategory(category: TaskCategory): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTaskCompletion(taskId: String, completed: Boolean)
}
