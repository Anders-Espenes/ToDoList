package com.example.todolist.views.todoListView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.data.Task
import com.example.todolist.data.TodoList
import com.example.todolist.databinding.TodoListFragmentBinding
import com.example.todolist.managers.TodoListManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.input_bottom_sheet.view.*
import kotlinx.android.synthetic.main.todo_list_fragment.view.*


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
        binding.todoListRecycler.adapter =
            TodoListAdapter(emptyList<TodoList>(), this::onTodoListClicked)

        TodoListManager.instance.onTodoList = {
            (binding.todoListRecycler.adapter as TodoListAdapter).updateTodoList(it)
        }

        TodoListManager.instance.load(this)

        // TODO: Make this a fragment?
        view.todoListFloatingButton.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            val bottomSheetView =
                layoutInflater.inflate(R.layout.input_bottom_sheet, container, false)

            bottomSheetView.sheetBtn.setOnClickListener {
                val text = bottomSheetView.sheetText.text.toString()
                addTodoList(text)

            }
            bottomSheet.setContentView(bottomSheetView)
            bottomSheet.show()
        }
        return view
    }


    private fun addTodoList(text: String) {
        val todoList = TodoList(text, mutableListOf<Task>())
        TodoListManager.instance.addTodoList(todoList)
        this.context?.let { TodoListManager.instance.save(it, todoList) }
    }

    private fun onTodoListClicked(todoList: TodoList) {
        val action = TodoListFragmentDirections.actionTodoListFragmentToTaskFragment(todoList)
        findNavController().navigate(action)
    }

}