package com.example.notes

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.Data.Note
import com.example.notes.Data.NotesViewModel
import com.example.notes.databinding.FragmentAddEditNoteBinding
import com.google.android.material.appbar.MaterialToolbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel
    private var reminderDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddEditNoteBinding.bind(view)

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        binding.apply {
            textEditTitle.setText(getNoteValue()?.title)
            notetextEd.setText(getNoteValue()?.text)
        }
        reminderDate = getNoteValue()?.reminderDate
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                saveNote()
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
                createToDoList()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote(){
        val note = getNoteValue()
        if (note != null) {
            val builder = AlertDialog.Builder(requireContext())
            builder
                .setTitle(getString(ALERT_DIALOG_TITLE_RES))
                .setMessage(getString(ALERT_DIALOG_MESSAGE_RES))
                .setPositiveButton(getString(DEL_BUTTON_TXT_RES)) { _, _ ->
                notesViewModel.deleteNote(note)
                Toast.makeText(requireContext(), "Successfully deleted", Toast.LENGTH_LONG).show()
                requireActivity().onBackPressed()
            }
            .setNegativeButton(getString(CANCEL_BUTTON_TXT_RES)) { _, _ -> }

            builder.show()
        } else {
            Toast.makeText(requireContext(), "Nothing to delete", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveNote(){
        binding.apply {
            val title = textEditTitle.text.toString()
            val text = notetextEd.text.toString()
            val creationDate = DateFormat.getDateInstance().format(Date())
            val changeDate = DateFormat.getDateInstance().format(Date())

            if (inputCheck(title, text)) {
                val oldNote = getNoteValue()
                if (oldNote != null) {
                    val upgradedNote = Note(
                        noteId = oldNote.noteId,
                        title = title,
                        text = text,
                        changeDate = changeDate,
                        creationDate = oldNote.creationDate,
                        reminderDate = reminderDate
                    )
                    notesViewModel.updateNote(upgradedNote)
                } else {
                    val note = Note(
                        noteId = 0,
                        title = title,
                        text = text,
                        creationDate = creationDate,
                        changeDate = changeDate,
                        reminderDate = reminderDate
                    )
                    notesViewModel.addNote(note)
                }
                Toast.makeText(requireContext(), "Your note was saved", Toast.LENGTH_LONG).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createToDoList() {
        val lParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lParams.gravity = Gravity.LEFT
        val rButton = RadioButton(requireContext())
        rButton.text = "sdads"
        val rGroup = binding.radioGroup
        rGroup.addView(rButton, lParams)
    }

    private fun share() {

    }

    private fun changeTheme() {

    }

    private fun inputCheck(title: String, text: String): Boolean {
        return !(title.isEmpty() && text.isEmpty())
    }

    private fun getNoteValue() = requireArguments().getParcelable<Note>(ARG_NOTE_VALUE)


    companion object {

        private const val ARG_NOTE_VALUE = "ARG_TITLE_VALUE"
        private const val DEL_BUTTON_TXT_RES = R.string.delete_dialog_button
        private const val CANCEL_BUTTON_TXT_RES = R.string.cancel_dialog_button
        private const val ALERT_DIALOG_TITLE_RES = R.string.dialog_title
        private const val ALERT_DIALOG_MESSAGE_RES = R.string.dialog_message

        @JvmStatic
        fun newInstance(note: Note?): AddEditNoteFragment {
            val args = Bundle().apply {
                putParcelable(ARG_NOTE_VALUE, note)
            }
            val fragment = AddEditNoteFragment()
            fragment.arguments = args
            return fragment
        }
    }
}