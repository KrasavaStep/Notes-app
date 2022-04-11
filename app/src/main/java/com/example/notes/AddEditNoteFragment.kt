package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.notes.Data.NotesViewModel
import com.example.notes.databinding.FragmentAddEditNoteBinding

class AddEditNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddEditNoteBinding
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.save -> saveNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(){

    }

    companion object {

        @JvmStatic
        fun newInstance() = AddEditNoteFragment()
    }
}