package com.example.todolist.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class TodoList(val text:String, val taskList: MutableList<Task>)
