package com.example.notes.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    var title: String = "",
    var text: String = "",
    var creationDate: Date,
    var changeDate: Date,
    var reminderDate: Date? = null
)