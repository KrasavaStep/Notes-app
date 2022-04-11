package com.example.notes.Data

import androidx.lifecycle.LiveData
import java.util.*

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotes:LiveData<List<Note>> = notesDao.getAllNotes()

    fun getSortedByNameNotes(title: String) : LiveData<List<Note>>{
        return notesDao.getSortedByNameNotes(title)
    }

    fun getSortedByDateNotes(date: Date): LiveData<List<Note>> {
        return notesDao.getSortedByDateNotes(date)
    }

    fun getSortedByChangeDateNotes(date: Date): LiveData<List<Note>> {
        return notesDao.getSortedByChangeDateNotes(date)
    }

    suspend fun addNote(note: Note){
        notesDao.addNote(note)
    }
}