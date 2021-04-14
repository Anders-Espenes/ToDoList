package com.example.todolist.services

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.todolist.data.TodoList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream


class TodoListService : Service() {

    val TAG: String = "TodoListService"

    var onSave: ((file: Uri) -> Unit)? = null

    private var auth: FirebaseAuth

    init {
        Log.d(TAG, "TodoListService is running...")
        auth = Firebase.auth
        signInAnonymously()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val todoList = intent?.getStringExtra("EXTRA_DATA")
        val load = intent?.getStringExtra("LOAD")

        if (todoList != null) { //
            Thread { // Make a new thread to not hold Main thread up
                val title = Gson().fromJson(todoList, TodoList::class.java).text
                todoList?.let {
                    Log.d(TAG, todoList)
                    onSave("$title-${auth.currentUser.uid}.json", todoList)
                }
            }.start()
        }

        // Download TodoList from Firebase
        if (load != null) {
            download()
        }


        return START_REDELIVER_INTENT     // Set to restart and and call last intent
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_LONG).show()
    }

    private fun onSave(fileName: String, content: String) {
        val path = this.getExternalFilesDir(null)
        if (path != null) {
            val file = File(path, fileName)
            FileOutputStream(file, false).bufferedWriter().use { writer ->
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
        val ref =
            FirebaseStorage.getInstance().reference.child("Todo/${auth.currentUser.uid}/${file.lastPathSegment}")
        val uploadTask = ref.putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Saved file to FireBase: ${it.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Error saving file to FireBase", it)
        }
    }

    private fun download() {
        val ref = FirebaseStorage.getInstance().reference.child("Todo/${auth.currentUser.uid}")
        val listOfFiles = ref.listAll() // Get a list of files in path


        listOfFiles.addOnSuccessListener {
            it.items.forEach {
                downloadFile(it)
            }

        }.addOnFailureListener {
            Log.e(TAG, "Error downloading todo List", it)
        }
    }

    private fun downloadFile(ref: StorageReference) {
        val tempFile = File.createTempFile("temp", "json")
        ref.getFile(tempFile).addOnSuccessListener {
            Log.d(TAG, "Successfully downloaded file")
            val content: String = tempFile.readText()
            broadcastDownload(content)  // Broadcast item to manager
        }.addOnFailureListener {
            Log.e(TAG, "Error downloading ${ref.name}", it)
            tempFile.delete()
        }
    }

    private fun broadcastDownload(content: String) {
        val intent = Intent("DownloadReady")
        intent.putExtra("TodoList", content)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }


    private fun signInAnonymously() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login successfully ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Login failed", it)
        }
    }

}