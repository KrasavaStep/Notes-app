package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.Data.AddEditFragmentViewModel
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.NotesRepository
import com.example.notes.Data.NotesViewModel

class VMFactory {

    fun initNotesVMFactory(): ViewModelProvider.Factory {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotesViewModel(NotesRepository(ApplicationContext.INSTANCE.notesDao)) as T
            }
        }
        return factory
    }

    fun initNotesAddEditScreenVMFactory(noteId: Int): ViewModelProvider.Factory {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddEditFragmentViewModel(NotesRepository(ApplicationContext.INSTANCE.notesDao), noteId) as T
            }
        }
        return factory
    }
}