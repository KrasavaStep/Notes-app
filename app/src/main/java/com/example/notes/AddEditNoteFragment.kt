package com.example.notes

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.Data.AddEditFragmentViewModel
import com.example.notes.Data.Entities.ToDoListItem
import com.example.notes.databinding.FragmentAddEditNoteBinding

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding
        get() = _binding!!

    private var noteId: Int = 0
    private lateinit var notesViewModel: AddEditFragmentViewModel
    private lateinit var adapter: TodosAdapter

    private var reminderDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        noteId = arguments?.getInt(ARG_NOTE_ID) ?: INVALID_ID
        val factory = VMFactory().initNotesAddEditScreenVMFactory(noteId)
        notesViewModel = ViewModelProvider(this, factory)[AddEditFragmentViewModel::class.java]
        adapter = TodosAdapter(layoutInflater, object : TodosAdapter.TodoEditListener {
            override fun onTextEdited(todo: ToDoListItem, text: String) {
                val newTodo = todo.copy(text = text)
                notesViewModel.updateTodoItem(newTodo)
            }

            override fun onDeleteBtnClicked(todo: ToDoListItem) {
                Log.d("todolist", "$todo")
                notesViewModel.deleteTodoItem(todo)
            }

            override fun onCheckBoxClicked(todo: ToDoListItem, isChecked: Boolean) {
                val newTodo = todo.copy(isDone = isChecked)
                notesViewModel.updateTodoItem(newTodo)
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddEditNoteBinding.bind(view)

        notesViewModel.currentNoteLiveData.observe(viewLifecycleOwner) {
            binding.notetextEd.setText(it?.text ?: "")
            binding.textEditTitle.setText(it?.title ?: "")
        }

        setRecycler()

        notesViewModel.noteErrorLiveData.observe(viewLifecycleOwner) {
            if (it.isInputError) Toast.makeText(
                requireContext(),
                it.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                saveNote()
                requireActivity().onBackPressed()
                return true
            }
            R.id.delete_note -> {
                deleteNote()
                return true
            }
            R.id.share -> {
                share()
                return true
            }
            R.id.layout_color -> {
                changeTheme()
                return true
            }
            R.id.to_do_list -> {
                saveTodo()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //TODO
    private fun deleteNote() {
        if (noteId != -1) {
            val builder = AlertDialog.Builder(requireContext())
            builder
                .setTitle(getString(ALERT_DIALOG_TITLE_RES))
                .setMessage(getString(ALERT_DIALOG_MESSAGE_RES))
                .setPositiveButton(getString(DEL_BUTTON_TXT_RES)) { _, _ ->
                    notesViewModel.deleteNote()
                    Toast.makeText(requireContext(), "Successfully deleted", Toast.LENGTH_LONG)
                        .show()
                    requireActivity().onBackPressed()
                }
                .setNegativeButton(getString(CANCEL_BUTTON_TXT_RES)) { _, _ -> }

            builder.show()
        } else {
            Toast.makeText(requireContext(), "Nothing to delete", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveNote() {
        binding.apply {
            val title = textEditTitle.text.toString()
            val text = notetextEd.text.toString()
            notesViewModel.saveNote(text, title, null)
        }
    }

    private fun saveTodo() {
        notesViewModel.saveTodo()
    }

    private fun setRecycler() {
        binding.todosRecycler.adapter = adapter
        binding.todosRecycler.layoutManager = LinearLayoutManager(requireContext())
        notesViewModel.getAllTodos.observe(viewLifecycleOwner) { todos ->
            adapter.setData(todos)
        }
    }

    private fun share() {

    }

    private fun changeTheme() {

    }

    companion object {

        private const val ARG_NOTE_ID = "NOTE_ID"
        private const val INVALID_ID = -1

        private const val DEL_BUTTON_TXT_RES = R.string.delete_dialog_button
        private const val CANCEL_BUTTON_TXT_RES = R.string.cancel_dialog_button
        private const val ALERT_DIALOG_TITLE_RES = R.string.dialog_title
        private const val ALERT_DIALOG_MESSAGE_RES = R.string.dialog_message

        @JvmStatic
        fun newInstance(noteId: Int): AddEditNoteFragment {
            val args = Bundle().apply {
                putInt(ARG_NOTE_ID, noteId)
            }
            val fragment = AddEditNoteFragment()
            fragment.arguments = args
            return fragment
        }
    }
}