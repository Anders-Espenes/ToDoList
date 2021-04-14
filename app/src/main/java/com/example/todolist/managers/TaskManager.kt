package com.example.todolist.managers

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todolist.data.Task
import com.example.todolist.data.TodoList
import com.example.todolist.services.TodoListService
import com.google.gson.Gson

class TaskManager {

    private val TAG: String = "TaskManager"
    private lateinit var todoList: TodoList

    var onTask: ((List<Task>) -> Unit)? = null
    var onTaskUpdate: ((task: Task) -> Unit)? = null

    fun load(todo : TodoList) {

       todoList = todo

        onTask?.invoke(todo.taskList)
    }

    fun addTask(task: Task) {
        Log.d(TAG, "Added task: ${task.toString()}")
        todoList.taskList.add(task)
        onTask?.invoke(todoList.taskList)
    }

    fun updateTask(task: Task) {
        onTaskUpdate?.invoke(task)
    }

    fun save(context: Context){
        Intent(context, TodoListService::class.java).also {
            Log.i(TAG, Gson().toJson(todoList))
            it.putExtra("EXTRA_DATA", Gson().toJson(todoList, TodoList::class.java))
            context.startService(it)
        }
    }

    companion object {
        val instance = TaskManager()
    }

}