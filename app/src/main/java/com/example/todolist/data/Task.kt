package com.example.todolist.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(val text:String, val checked:Boolean) : Parcelable
