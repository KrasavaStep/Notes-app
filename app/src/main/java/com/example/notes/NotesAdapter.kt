package com.example.notes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Data.Entities.Note
import com.example.notes.databinding.NotesListItemBinding

class NotesAdapter(
    private val layoutInflater: LayoutInflater,
    private val clickListener: NoteClickListener
) : ListAdapter<Note, NotesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotesListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            noteTime.text = item.changeDate
            noteBody.text = item.text
            noteHeader.text = item.title
        }
    }

    fun setData(notes: List<Note>) {
        submitList(notes.toMutableList())
    }

    inner class ViewHolder(val binding: NotesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val note = getItem(adapterPosition)
                    clickListener.onNoteClicked(note)
                }
            }
        }
    }

    interface NoteClickListener {
        fun onNoteClicked(note: Note)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.noteId == newItem.noteId
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }
}