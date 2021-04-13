package com.example.todolist.views.todoListView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.data.Task
import com.example.todolist.data.TodoList
import com.example.todolist.databinding.TodoListFragmentBinding
import com.example.todolist.managers.TodoListManager


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
        binding.todoListRecycler.adapter = TodoListAdapter(emptyList<TodoList>())

        TodoListManager.instance.onTodoList = {
            (binding.todoListRecycler.adapter as TodoListAdapter).updateTodoList(it)
        }

        TodoListManager.instance.load()

        return view
    }

    private fun addTodoList(text:String) {
        TodoListManager.instance.addTodoList(TodoList(text, emptyList<Task>() as MutableList<Task>))
    }

}