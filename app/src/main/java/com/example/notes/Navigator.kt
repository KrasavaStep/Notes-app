package com.example.notes

import androidx.fragment.app.Fragment
import com.example.notes.Data.Entities.Note

interface Navigator {
    fun navigateToAddEditNoteScreen(noteId: Int)
}

fun Fragment.navigator() = (requireActivity() as Navigator)
