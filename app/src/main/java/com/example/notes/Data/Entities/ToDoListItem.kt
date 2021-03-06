package com.example.notes.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_table")
data class ToDoListItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,
    val noteId: Int,
    val isDone: Boolean,
    val text: String
){
    override fun equals(other: Any?): Boolean {
        if (other is ToDoListItem) {
            return other.itemId == itemId && other.isDone == isDone
        }
        return false
    }
}