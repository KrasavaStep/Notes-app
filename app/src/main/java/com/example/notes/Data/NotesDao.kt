package com.example.notes.Data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.*
import java.util.*

@Dao
interface NotesDao {

    @Insert(onConflict = IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY noteId ASC")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE title = :title ORDER BY title ASC")
    fun getSortedByNameNotes(title: String) : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE creationDate = :creationDate ORDER BY creationDate ASC")
    fun getSortedByDateNotes(creationDate: String) : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE changeDate = :changeDate ORDER BY changeDate ASC")
    fun getSortedByChangeDateNotes(changeDate: String) : LiveData<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}