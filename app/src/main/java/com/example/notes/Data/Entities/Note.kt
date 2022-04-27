package com.example.notes.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    var title: String = "",
    var text: String = "",
    var creationDate: String,
    var changeDate: String,
    var reminderDate: String? = null,
)