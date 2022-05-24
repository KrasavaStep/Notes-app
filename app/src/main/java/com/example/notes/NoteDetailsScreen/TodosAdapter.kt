package com.example.notes.NoteDetailsScreen

import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Data.Entities.ToDoListItem
import com.example.notes.databinding.TodoListItemBinding

class TodosAdapter(
    private val layoutInflater: LayoutInflater,
    private val todoEditListener: TodoEditListener
) :
    ListAdapter<ToDoListItem, TodosAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.todoDelBtn.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val todo = getItem(adapterPosition)
                    todoEditListener.onDeleteBtnClicked(todo)
                }
            }
            binding.todoText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?, start: Int, before: Int, count: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        val todo = getItem(adapterPosition)
                        todoEditListener.onTextEdited(todo, s.toString())
                    }
                }

            })

            binding.todoCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val todo = getItem(adapterPosition)
                    todoEditListener.onCheckBoxClicked(todo, isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            todoText.setText(item.text)
            todoCheckbox.isChecked = item.isDone
            Log.d("logmy", item.isDone.toString())
            if (item.isDone) {
                todoText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else todoText.paintFlags = Paint.LINEAR_TEXT_FLAG
        }
    }

    fun setData(todos: List<ToDoListItem>) {
        submitList(todos.toMutableList())
    }

    interface TodoEditListener {
        fun onTextEdited(todo: ToDoListItem, text: String)
        fun onDeleteBtnClicked(todo: ToDoListItem)
        fun onCheckBoxClicked(todo: ToDoListItem, isChecked: Boolean)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ToDoListItem> =
            object : DiffUtil.ItemCallback<ToDoListItem>() {
                override fun areItemsTheSame(
                    oldItem: ToDoListItem,
                    newItem: ToDoListItem
                ): Boolean {
                    return oldItem.noteId == newItem.noteId
                }

                override fun areContentsTheSame(
                    oldItem: ToDoListItem,
                    newItem: ToDoListItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}