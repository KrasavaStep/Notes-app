package com.example.notes.Data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    var title: String = "",
    var text: String = "",
    var creationDate: String,
    var changeDate: String,
    var reminderDate: String? = null,

//    @Ignore
//    var todo: ArrayList<String>? = null
):Parcelable