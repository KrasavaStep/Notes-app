package com.example.notes.Data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val getAllNotes: LiveData<List<Note>>
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
}