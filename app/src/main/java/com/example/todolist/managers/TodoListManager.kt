package com.example.todolist.managers

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Task
import com.example.todolist.data.TodoList
import com.example.todolist.services.TodoListService
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class TodoListManager {
    private lateinit var todoLists: MutableList<TodoList>

    var onTodoList: ((List<TodoList>) -> Unit)? = null
    var onTodoListUpdate: ((task: TodoList) -> Unit)? = null

    fun load() {
        // TODO: Implement, temp data

        val temp = mutableListOf(
            Task("Clean bedroom", false),
            Task("Go shopping", false),
            Task("Go for a walk", true),
            Task("Test this app", false)
        )

        val temp2 = mutableListOf(
            Task("Second Todo List", false),
            Task("Go shopping", false),
            Task("Go for a walk", true),
            Task("Test this app", false)
        )

        todoLists = mutableListOf(
                TodoList("Daily Task", temp),
                TodoList("Todo List", temp2)
        )

        onTodoList?.invoke(todoLists)
    }

    fun addTodoList(todoList:TodoList){
        Log.i("TodoListManager","todoList item was added to todoLists: ${todoList.text}" )
        todoLists.add(todoList)
        onTodoList?.invoke(todoLists)
    }

    fun updateTodoList(todoList:TodoList){
        onTodoListUpdate?.invoke(todoList)
    }

    fun getPosition(todoList:TodoList): Int{
        return todoLists.indexOf(todoList)
    }

    fun save(context:Context) {
        Intent(context, TodoListService::class.java).also {
            val gson = Gson()
            val dataString = gson.toJson(todoLists)
            Log.i("TodoList", dataString)
            it.putExtra("EXTRA_DATA", dataString)
            context.startService(it)
        }


    }

    companion object {
        val instance = TodoListManager()
    }

}