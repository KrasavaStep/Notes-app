package com.example.notes.Data

import android.app.Application
import androidx.lifecycle.*
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.Entities.ToDoListItem
import com.example.notes.Data.NotesDatabase
import com.example.notes.Data.NotesRepository
import com.example.notes.DataEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val getAllNotes: LiveData<List<Note>> = repository.getAllNotes

    private val _openAddEditNoteScreen = MutableLiveData<DataEvent<Int>>();
    val openAddEditNoteScreen: LiveData<DataEvent<Int>> = _openAddEditNoteScreen

//    fun addNote(note: Note){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addNote(note)
//        }
//    }





//    fun getToDoListForNote(noteId: Int): LiveData<List<ToDoListItem>>{
//        return repository.getToDoItemsForNote(noteId)
//    }
//
//    fun updateNote(note: Note){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateNote(note)
//        }
//    }
//
//    fun updateToDoListItem(toDoItem: ToDoListItem){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateToDoListItem(toDoItem)
//        }
//    }
//
//
//
//    fun deleteToDoItem(toDoItem: ToDoListItem){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteToDoItem(toDoItem)
//        }
//    }
//
//    fun addToDoListItem(toDoItem: ToDoListItem){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addToDoListItem(toDoItem)
//        }
//    }

    fun onNoteClicked(note: Note){
        _openAddEditNoteScreen.value = DataEvent(note.noteId)
    }
}