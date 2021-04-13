package com.example.todolist.views.todoListView

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TodoList
import com.example.todolist.databinding.TodoListLayoutBinding

class TodoListAdapter(private var todoLists:List<TodoList>) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    class ViewHolder(val binding:TodoListLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(todoList: TodoList) {
            binding.text.text = todoList.text
        }
    }

        override fun getItemCount(): Int = todoLists.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val todoList = todoLists[position]
            holder.bind(todoList)
        }

        fun updateTodoList(newTodoList:List<TodoList>) {
            todoLists = newTodoList
           notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodoListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}