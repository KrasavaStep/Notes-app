package com.example.notes.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.Entities.ToDoListItem

@Database(entities = [Note::class, ToDoListItem::class], version = 1, exportSchema = false)
abstract class NotesDatabase () : RoomDatabase() {

    abstract fun notesDao() : NotesDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context) : NotesDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}