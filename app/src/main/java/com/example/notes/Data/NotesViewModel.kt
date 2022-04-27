package com.example.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.Entities.ToDoListItem
import com.example.notes.Data.NotesDatabase
import com.example.notes.Data.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    val getAllNotes: LiveData<List<Note>>
    private val repository: NotesRepository

    init {
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao)
        getAllNotes = repository.getAllNotes
    }

    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun getToDoListForNote(noteId: Int): LiveData<List<ToDoListItem>>{
        return repository.getToDoItemsForNote(noteId)
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun updateToDoListItem(toDoItem: ToDoListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateToDoListItem(toDoItem)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun deleteToDoItem(toDoItem: ToDoListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteToDoItem(toDoItem)
        }
    }

    fun addToDoListItem(toDoItem: ToDoListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToDoListItem(toDoItem)
        }
    }
}