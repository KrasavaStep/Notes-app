package com.example.notes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.NotesRepository
import com.example.notes.Data.NotesViewModel
import com.example.notes.databinding.FragmentNotesListBinding

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    private var _binding: FragmentNotesListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val factory = VMFactory().initNotesVMFactory()
        notesViewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]
        adapter = NotesAdapter(layoutInflater, object : NotesAdapter.NoteClickListener{
            override fun onNoteClicked(note: Note) {
                notesViewModel.onNoteClicked(note)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNotesListBinding.bind(view)

        binding.apply {
            floatingActionButton.setOnClickListener {
                navigator().navigateToAddEditNoteScreen(-1)
            }
        }

        setRecycler()


        notesViewModel.openAddEditNoteScreen.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { id ->
                navigator().navigateToAddEditNoteScreen(id)
            }
        }
    }

    private fun setRecycler(){
        binding.notesRecycler.adapter = adapter
        binding.notesRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        notesViewModel.getAllNotes.observe(viewLifecycleOwner, Observer { users ->
            adapter.setData(users)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }
}