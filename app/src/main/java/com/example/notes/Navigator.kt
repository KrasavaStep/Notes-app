package com.example.notes

import androidx.fragment.app.Fragment

interface Navigator {
    fun navigateToAddEditNoteScreen(noteId: Int)
    fun goBack()
}

fun Fragment.navigator() = (requireActivity() as Navigator)
