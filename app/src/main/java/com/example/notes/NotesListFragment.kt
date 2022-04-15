package com.example.notes

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.Data.NotesViewModel
import com.example.notes.databinding.FragmentNotesListBinding

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private lateinit var notesViewModel: NotesViewModel

    private var _binding: FragmentNotesListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNotesListBinding.bind(view)

        binding.apply {
            floatingActionButton.setOnClickListener {
                navigator().navigateToAddEditNoteScreen(note = null)
            }
            val adapter = NotesAdapter(navigator())
            notesRecycler.adapter = adapter
            notesRecycler.layoutManager = LinearLayoutManager(requireContext())

            notesViewModel = ViewModelProvider(this@NotesListFragment)[NotesViewModel::class.java]
            notesViewModel.getAllNotes.observe(viewLifecycleOwner, Observer { users ->
                adapter.setData(users)
            })
        }
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