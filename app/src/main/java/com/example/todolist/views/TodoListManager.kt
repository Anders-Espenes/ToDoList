package com.example.todolist.views

import com.example.todolist.data.Task

class TodoListManager {
    private lateinit var todoList: MutableList<Task>

    var onTask: ((List<Task>) -> Unit)? = null
    var onTaskUpdate: ((task: Task) -> Unit)? = null

    fun load() {
        // TODO: Implement, temp data

        todoList = mutableListOf(
            Task("Clean bedroom", false),
            Task("Go shopping", false),
            Task("Go for a walk", true),
            Task("Test this app", false)
        )

        onTask?.invoke(todoList)
    }

    fun addTask(text:String){
        todoList.add(Task(text, false))
    }

    companion object {
        val instance = TodoListManager()
    }

}