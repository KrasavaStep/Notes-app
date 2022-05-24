package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.NoteDetailsScreen.AddEditFragmentViewModel
import com.example.notes.Data.NotesRepository
import com.example.notes.NoteListScreen.NotesViewModel

class VMFactory {

    fun initNotesScreenVMFactory(): ViewModelProvider.Factory {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotesViewModel(NotesRepository(ApplicationContext.INSTANCE.notesDao)) as T
            }
        }
        return factory
    }

    fun initAddEditScreenVMFactory(noteId: Int): ViewModelProvider.Factory {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddEditFragmentViewModel(NotesRepository(ApplicationContext.INSTANCE.notesDao), noteId) as T
            }
        }
        return factory
    }
}