package com.example.notes.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_table")
data class ToDoListItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,
    val noteId: Int,
    val idInList: Int,
    val text: String
)