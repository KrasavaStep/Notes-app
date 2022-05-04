package com.example.notes.Data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.Entities.ToDoListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class AddEditFragmentViewModel(private val repository: NotesRepository, private val noteId: Int) :
    ViewModel() {

    val currentNoteLiveData = repository.getNoteById(noteId)

    val getAllTodos: LiveData<List<ToDoListItem>> = repository.getToDoItemsForNote(noteId)

    data class NoteError(var isInputError: Boolean = false, var errorMessage: String = "")

    private var noteError = NoteError()
    private val _noteErrorLiveData = MutableLiveData(noteError)
    val noteErrorLiveData: LiveData<NoteError> = _noteErrorLiveData

    fun saveNote(noteText: String, noteTitle: String, remainderDate: String?) {
        if (!inputCheck(noteText, noteTitle)) {
            noteError = noteError.copy(isInputError = true, errorMessage = "fill out all fields")
            _noteErrorLiveData.value = noteError
            return
        }

        noteError = noteError.copy(isInputError = false, errorMessage = "")
        _noteErrorLiveData.value = noteError

        val creationDate = DateFormat.getDateInstance().format(Date())
        val changeDate = DateFormat.getDateInstance().format(Date())

        viewModelScope.launch(Dispatchers.IO) {
            if (noteId == -1) {
                val note = Note(0, noteTitle, noteText, creationDate, changeDate, remainderDate)
                repository.addNote(note)
            } else {
                val note =
                    Note(noteId, noteTitle, noteText, creationDate, changeDate, remainderDate)
                repository.updateNote(note)
            }
        }
    }

    fun saveTodo() {
        if (noteId == -1) {
            noteError = noteError.copy(isInputError = true, errorMessage = "You must to save note")
            _noteErrorLiveData.value = noteError
            return
        }
        noteError = noteError.copy(isInputError = false, errorMessage = "")
        _noteErrorLiveData.value = noteError

        viewModelScope.launch(Dispatchers.IO) {
            val todo = ToDoListItem(0, noteId, false, "")

            repository.addToDoListItem(todo)
        }
    }

    fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(noteId)
        }
    }

    fun updateTodoItem(todo: ToDoListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateToDoListItem(todo)
        }
    }

    fun deleteTodoItem(todo: ToDoListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteToDoItem(todo)
        }
    }

    private fun inputCheck(title: String, text: String): Boolean {
        return !(title.isEmpty() && text.isEmpty())
    }
}