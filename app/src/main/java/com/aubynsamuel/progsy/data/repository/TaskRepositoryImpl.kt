package com.aubynsamuel.progsy.data.repository

import com.aubynsamuel.progsy.data.local.TaskDao
import com.aubynsamuel.progsy.domain.model.Task
import com.aubynsamuel.progsy.domain.model.TaskCategory
import com.aubynsamuel.progsy.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTasksByCategory(category: TaskCategory): Flow<List<Task>> =
        taskDao.getTasksByCategory(category)

    override suspend fun getTaskById(id: String): Task? = taskDao.getTaskById(id)

    override suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    override suspend fun updateTaskCompletion(taskId: String, completed: Boolean) =
        taskDao.updateTaskCompletion(taskId, completed)
}