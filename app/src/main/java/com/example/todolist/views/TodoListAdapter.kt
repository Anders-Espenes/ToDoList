package com.example.todolist.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.Task
import com.example.todolist.databinding.TaskLayoutBinding

class TodoListAdapter(private var tasks:List<Task>) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    class ViewHolder(val binding:TaskLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task){
            binding.taskText.text = task.text
            binding.taskCheckBox.isChecked = task.checked
        }
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateTasks(newTasks:List<Task>){
        tasks = newTasks
        notifyDataSetChanged()
    }
}