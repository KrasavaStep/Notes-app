package com.example.notes

import androidx.fragment.app.Fragment
import com.example.notes.Data.Note

interface Navigator {
    fun navigateToAddEditNoteScreen(note: Note?)
}

fun Fragment.navigator() = (requireActivity() as Navigator)
