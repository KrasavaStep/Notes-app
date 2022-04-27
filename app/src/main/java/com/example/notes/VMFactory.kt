package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.Data.NotesRepository
import com.example.notes.Data.NotesViewModel

class NotesVMFactory {

    private fun initViewModel(){
        val factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotesViewModel(NotesRepository(ApplicationContext.INSTANCE.notesDao)) as T
            }
        }
        notesViewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]
    }
}