package com.example.notes.NoteListScreen

import androidx.lifecycle.*
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.NotesRepository
import com.example.notes.DataEvent

class NotesViewModel(repository: NotesRepository) : ViewModel() {
    val getAllNotes: LiveData<List<Note>> = repository.getAllNotes

    private val _openAddEditNoteScreen = MutableLiveData<DataEvent<Int>>();
    val openAddEditNoteScreen: LiveData<DataEvent<Int>> = _openAddEditNoteScreen

    fun onNoteClicked(note: Note){
        _openAddEditNoteScreen.value = DataEvent(note.noteId)
    }
}