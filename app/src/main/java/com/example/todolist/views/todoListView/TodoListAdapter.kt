package com.example.todolist.views.todoListView

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TodoList
import com.example.todolist.databinding.TodoListLayoutBinding

class TodoListAdapter(
    private var todoLists: List<TodoList>,
    private val onTodoListClicked: (TodoList) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TodoListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoList: TodoList, onTodoListClicked: (TodoList) -> Unit) {
            binding.text.text = todoList.text

            binding.card.setOnClickListener {
                onTodoListClicked(todoList)
            }
        }
    }

    override fun getItemCount(): Int = todoLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoList = todoLists[position]
        holder.bind(todoList, onTodoListClicked)
    }

    fun updateTodoList(newTodoList: List<TodoList>) {
        todoLists = newTodoList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}