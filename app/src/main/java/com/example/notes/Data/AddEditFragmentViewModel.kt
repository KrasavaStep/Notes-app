package com.example.notes.Data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.Data.Entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class AddEditFragmentViewModel(private val repository: NotesRepository, private val noteId: Int) :
    ViewModel() {

    val currentNoteLiveData = repository.getNoteById(noteId)

    private val _isInputError = MutableLiveData<Boolean>()
    val isInputError: LiveData<Boolean> = _isInputError

    fun saveNote(noteId: Int, noteText: String, noteTitle: String, remainderDate: String?) {
        if (!inputCheck(noteText, noteTitle)) {
            _isInputError.value = true
            return
        }

        _isInputError.value = false

        viewModelScope.launch(Dispatchers.IO) {
            val creationDate = DateFormat.getDateInstance().format(Date())
            val changeDate = DateFormat.getDateInstance().format(Date())
            if (noteId == -1) {
                val note = Note(0, noteTitle, noteText, creationDate, changeDate, remainderDate)
                repository.addNote(note)
            } else {
                val note = Note(noteId, noteTitle, noteText, creationDate, changeDate, remainderDate)
                repository.updateNote(note)
            }
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(noteId)
        }
    }

    private fun inputCheck(title: String, text: String): Boolean {
        return !(title.isEmpty() && text.isEmpty())
    }
}