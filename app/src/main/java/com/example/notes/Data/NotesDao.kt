package com.example.notes.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query
import java.util.*

@Dao
interface NotesDao {

    @Insert(onConflict = REPLACE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY noteId ASC")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE title = :title ORDER BY title ASC")
    fun getSortedByNameNotes(title: String) : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE creationDate = :creationDate ORDER BY creationDate ASC")
    fun getSortedByDateNotes(creationDate: Date) : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE changeDate = :changeDate ORDER BY changeDate ASC")
    fun getSortedByChangeDateNotes(changeDate: Date) : LiveData<List<Note>>
}