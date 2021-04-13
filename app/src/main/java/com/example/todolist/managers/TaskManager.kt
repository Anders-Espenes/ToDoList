package com.example.todolist.managers

import com.example.todolist.data.Task

class TaskManager {
    private lateinit var taskList: MutableList<Task>

    var onTask: ((List<Task>) -> Unit)? = null
    var onTaskUpdate: ((task: Task) -> Unit)? = null

    fun load(tasks: MutableList<Task> = mutableListOf<Task>()) {

        taskList = tasks

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