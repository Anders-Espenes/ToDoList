package com.example.todolist.views.taskView

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.data.Task
import com.example.todolist.databinding.TaskFragmentBinding
import com.example.todolist.managers.TaskManager
import com.example.todolist.views.taskView.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.input_bottom_sheet.view.*
import kotlinx.android.synthetic.main.task_fragment.view.*
import kotlinx.android.synthetic.main.todo_list_fragment.view.*

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

        TaskManager.instance.load(args.todoList)
        view.taskFloatingButton.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            val bottomSheetView =
                layoutInflater.inflate(R.layout.input_bottom_sheet, container, false)

            bottomSheetView.sheetBtn.setOnClickListener {
                val text = bottomSheetView.sheetText.text.toString()
                addTask(text)

            }
            bottomSheet.setContentView(bottomSheetView)
            bottomSheet.show()
        }

        return view
    }

    private fun addTask(text: String) {
        TaskManager.instance.addTask(Task(text, false))
        this.context?.let { TaskManager.instance.save(it) }
    }

}