package com.example.todolist.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoList(val text: String, val taskList: MutableList<Task>) : Parcelable {

    override fun toString(): String {
        return "TodoList [text: ${text}, taskList: ${taskList}]"
    }
}


