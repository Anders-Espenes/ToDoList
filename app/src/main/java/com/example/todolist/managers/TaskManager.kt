package com.example.todolist.managers

import com.example.todolist.data.Task

class TaskManager {
    private lateinit var taskList: MutableList<Task>

    var onTask: ((List<Task>) -> Unit)? = null
    var onTaskUpdate: ((task: Task) -> Unit)? = null

    fun load() {
        // TODO: Implement, temp data

        taskList = mutableListOf(
                Task("Clean bedroom", false),
                Task("Go shopping", false),
                Task("Go for a walk", true),
                Task("Test this app", false)
        )

        onTask?.invoke(taskList)
    }

    fun addTask(task:Task){
        taskList.add(task)
    }

    fun updateTask(task:Task){
        onTaskUpdate?.invoke(task)
    }

    companion object {
        val instance = TaskManager()
    }

}