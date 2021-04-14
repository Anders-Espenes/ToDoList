package com.example.todolist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todolist.managers.TodoListManager

class BroadcastReceiver : BroadcastReceiver() {
    private val TAG: String = "BroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("TodoList")
        message?.let {
            Log.i(TAG, "Broadcast recieved the download")
            TodoListManager.instance.read(message)
        }
    }
}