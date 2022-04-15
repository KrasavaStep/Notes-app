package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Data.Note

class NotesAdapter(val navigator: Navigator) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notesList = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val viewObject = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.notes_list_item, parent, false)
        return ViewHolder(viewObject)
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val currentItem = notesList[position]
        holder.itemTime.text = currentItem.changeDate
        holder.itemBody.text = currentItem.text
        holder.itemHeader.text = currentItem.title

        holder.itemLayout.setOnClickListener{
            navigator.navigateToAddEditNoteScreen(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setData(notesList : List<Note>){
        this.notesList = notesList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemHeader: TextView
        var itemBody: TextView
        var itemTime: TextView
        var itemLayout: View

        init {
            itemHeader = itemView.findViewById(R.id.note_header)
            itemBody = itemView.findViewById(R.id.note_body)
            itemTime = itemView.findViewById(R.id.note_time)
            itemLayout = itemView.findViewById(R.id.item_layout)
        }
    }
}