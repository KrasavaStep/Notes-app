package com.example.notes

import android.app.Application
import com.example.notes.Data.NotesDao
import com.example.notes.Data.NotesDatabase

class ApplicationContext : Application() {
    lateinit var notesDao: NotesDao
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        notesDao = NotesDatabase.getDatabase(INSTANCE).notesDao()
    }

    companion object {
        lateinit var INSTANCE: ApplicationContext
    }
}