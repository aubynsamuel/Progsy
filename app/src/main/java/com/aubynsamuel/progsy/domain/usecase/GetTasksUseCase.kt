package com.aubynsamuel.progsy.domain.usecase

import com.aubynsamuel.progsy.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository,
) {
    operator fun invoke() = repository.getAllTasks()
}