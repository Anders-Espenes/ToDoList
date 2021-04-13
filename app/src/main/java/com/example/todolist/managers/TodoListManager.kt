package com.example.todolist.managers

import androidx.lifecycle.ViewModel
import com.example.todolist.data.Task
import com.example.todolist.data.TodoList

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
        todoLists.add(todoList)
    }

    fun updateTodoList(todoList:TodoList){
        onTodoListUpdate?.invoke(todoList)
    }

    fun getPosition(todoList:TodoList): Int{
        return todoLists.indexOf(todoList)
    }

    companion object {
        val instance = TodoListManager()
    }

}