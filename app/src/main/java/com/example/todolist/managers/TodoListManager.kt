package com.example.todolist.managers

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todolist.data.TodoList
import com.example.todolist.services.TodoListService
import com.example.todolist.views.todoListView.TodoListFragment
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.Gson

class TodoListManager {

    private var TAG: String = "TodoListManager"
    private lateinit var todoLists: MutableList<TodoList>

    var onTodoList: ((List<TodoList>) -> Unit)? = null
    var onTodoListUpdate: ((task: TodoList) -> Unit)? = null

    fun load(fragment: TodoListFragment) {

        todoLists = mutableListOf() // Create empty list

        if (fragment.context != null) {
            Intent(fragment.context, TodoListService::class.java).also {
                it.putExtra("LOAD", "true")
                fragment.context?.startService(it)
            }
        }
    }

    fun addTodoList(todoList: TodoList) {
        Log.i("TodoListManager", "todoList item was added to todoLists: ${todoList.text}")
        todoLists.add(todoList)
        onTodoList?.invoke(todoLists)
    }

    fun updateTodoList(todoList: TodoList) {
        onTodoListUpdate?.invoke(todoList)
    }

    fun getPosition(todoList: TodoList): Int {
        return todoLists.indexOf(todoList)
    }

    fun save(context: Context, todoList: TodoList) {
        Intent(context, TodoListService::class.java).also {
            Log.i(TAG, Gson().toJson(todoList))
            it.putExtra("EXTRA_DATA", Gson().toJson(todoList))
            context.startService(it)
        }
    }

    fun read(text: String) {
        val todoList = Gson().fromJson(text, TodoList::class.java)
        addTodoList(todoList)
    }

    companion object {
        val instance = TodoListManager()
    }

}