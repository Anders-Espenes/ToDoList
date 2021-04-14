package com.example.todolist.services

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.example.todolist.data.TodoList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileOutputStream


class TodoListService : Service() {

    val TAG: String = "TodoList:TodoListService"

    var onSave: ((file: Uri) -> Unit)? = null

    private var auth: FirebaseAuth

    private lateinit var todoList: MutableList<TodoList>

    init {
        Log.d(TAG, "TodoListService is running...")
        auth = Firebase.auth
        signInAnonymously()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val todoList = intent?.getStringExtra("EXTRA_DATA")
        todoList?.let {
            Log.d(TAG, todoList)
            onSave("test.todo", todoList)
        }
        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_LONG).show()
    }

    private fun onSave(fileName: String, content: String) {
        val path = this.getExternalFilesDir(null)
        if (path != null) {
            val file = File(path, fileName)
            FileOutputStream(file, true).bufferedWriter().use { writer ->
                writer.write(content)
            }
            this.onSave?.invoke(file.toUri())
            upload(file.toUri())
        } else {
            Toast.makeText(this, "Couldn't save to file", Toast.LENGTH_LONG).show()
            Log.e(TAG, "Filepath not found")
        }
    }

    private fun upload(file: Uri) {
        val ref = FirebaseStorage.getInstance().reference.child("Todo/${file.lastPathSegment}")
        val uploadTask = ref.putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Saved file to FireBase: ${it.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Error saving file to FireBase", it)
        }
    }

    private fun signInAnonymously() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login successfully ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Login failed", it)
        }
    }



}