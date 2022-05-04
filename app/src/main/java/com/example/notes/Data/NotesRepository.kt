package com.example.notes.Data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.Entities.ToDoListItem

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotes:LiveData<List<Note>> = notesDao.getAllNotes()

    fun getSortedByNameNotes(title: String) : LiveData<List<Note>>{
        return notesDao.getSortedByNameNotes(title)
    }

    fun getSortedByDateNotes(date: String): LiveData<List<Note>> {
        return notesDao.getSortedByDateNotes(date)
    }

    fun getSortedByChangeDateNotes(date: String): LiveData<List<Note>> {
        return notesDao.getSortedByChangeDateNotes(date)
    }

    fun getToDoItemsForNote(noteId: Int): LiveData<List<ToDoListItem>>{
        return notesDao.getToDoItemsForNote(noteId)
    }

    suspend fun addNote(note: Note){
        notesDao.addNote(note)
    }

    fun getNoteById(noteId: Int)= notesDao.getNoteById(noteId)

    suspend fun addToDoListItem(toDoItem: ToDoListItem){
        notesDao.addToDoItem(toDoItem)
    }

    suspend fun updateNote(note: Note){
        notesDao.updateNote(note)
    }

    suspend fun updateToDoListItem(toDoItem: ToDoListItem){
        notesDao.updateToDoListItem(toDoItem)
    }

    suspend fun deleteNote(noteId: Int){
        notesDao.deleteToDoList(noteId)
        notesDao.deleteNote(noteId)
    }

    suspend fun deleteToDoItem(toDoItem: ToDoListItem){
        notesDao.deleteToDoItem(toDoItem)
    }

    interface RepositoryChangeListener {
        fun onUserListUpdated(newList: List<Note>)
    }
}