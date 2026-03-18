package com.avinash.taskflow.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.avinash.taskflow.model.TaskStats
import androidx.compose.runtime.State
import com.avinash.taskflow.model.Task

class DashboardViewModel {

    private val _tasks = mutableStateOf(
        listOf(
            Task(1, "Learn Compose"),
            Task(2, "Build Task App")
        )
    )
    val tasks: State<List<Task>> = _tasks

    private val _stats = mutableStateOf(
        TaskStats(
            total = _tasks.value.size,
            pending = _tasks.value.size,
            completed = 0
        )
    )
    val stats: State<TaskStats> = _stats

    fun addTask(title: String) {
        val currentTasks = _tasks.value

        val newTask = Task(
            id = currentTasks.size + 1,
            title = title
        )

        _tasks.value = currentTasks + newTask
        updateStats(_tasks.value)
    }

    fun toggleTask(taskId: Int) {
        val updatedTasks = _tasks.value.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else task
        }

        _tasks.value = updatedTasks
        updateStats(updatedTasks)
    }

    fun deleteTask(taskId: Int) {
        val updatedTasks = _tasks.value.filter { it.id != taskId }

        _tasks.value = updatedTasks
        updateStats(updatedTasks)
    }

    private fun updateStats(tasks: List<Task>) {
        val total = tasks.size
        val completed = tasks.count { it.isCompleted }
        val pending = total - completed

        _stats.value = TaskStats(
            total = total,
            pending = pending,
            completed = completed
        )
    }
}