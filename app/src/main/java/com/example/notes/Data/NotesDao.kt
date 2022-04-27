package com.example.notes.Data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.Entities.ToDoListItem

@Dao
interface NotesDao {

    @Insert(onConflict = IGNORE)
    suspend fun addNote(note: Note)

    @Insert(onConflict = IGNORE)
    suspend fun addToDoItem(toDoItem: ToDoListItem)

    @Query("SELECT * FROM notes_table ORDER BY noteId ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE noteId = :noteId")
    fun getNoteById(noteId: Int): LiveData<Note>

    @Query("SELECT * FROM notes_table WHERE title = :title ORDER BY title ASC")
    fun getSortedByNameNotes(title: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE creationDate = :creationDate ORDER BY creationDate ASC")
    fun getSortedByDateNotes(creationDate: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE changeDate = :changeDate ORDER BY changeDate ASC")
    fun getSortedByChangeDateNotes(changeDate: String): LiveData<List<Note>>

    @Query("SELECT * FROM list_table WHERE noteId = :noteId")
    fun getToDoItemsForNote(noteId: Int): LiveData<List<ToDoListItem>>

    @Update
    suspend fun updateNote(note: Note)

    @Update
    suspend fun updateToDoListItem(item: ToDoListItem)

    @Query("DELETE FROM notes_table WHERE noteId = :noteId")
    suspend fun deleteNote(noteId: Int)

    @Delete
    suspend fun deleteToDoItem(toDoItem: ToDoListItem)

    @Query("DELETE FROM list_table WHERE noteId = :noteId")
    suspend fun deleteToDoList(noteId: Int)
}