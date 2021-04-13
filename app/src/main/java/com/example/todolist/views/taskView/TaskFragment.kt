package com.example.todolist.views.taskView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.data.Task
import com.example.todolist.databinding.TaskFragmentBinding
import com.example.todolist.managers.TaskManager
import com.example.todolist.views.taskView.TaskAdapter

class TaskFragment : Fragment() {

    private var _binding: TaskFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TaskFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.taskRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.taskRecycler.adapter = TaskAdapter(emptyList<Task>())

        TaskManager.instance.onTask = {
            (binding.taskRecycler.adapter as TaskAdapter).updateTasks(it)
        }

        TaskManager.instance.load(args.todoList.taskList)

        return view
    }


    private fun addTask(text: String) {
        TaskManager.instance.addTask(Task(text, false))
    }
}