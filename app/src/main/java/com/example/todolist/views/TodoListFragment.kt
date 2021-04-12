package com.example.todolist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.data.Task
import com.example.todolist.databinding.TodoListFragmentBinding

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TodoListFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.todoListRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.todoListRecycler.adapter = TodoListAdapter(emptyList<Task>())

        TodoListManager.instance.onTask = {
            (binding.todoListRecycler.adapter as TodoListAdapter).updateTasks(it)
        }

        TodoListManager.instance.load()

        return view
    }

    private fun addTask(text:String) {
        TodoListManager.instance.addTask(text)
    }


}