package com.example.notes

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.Data.AddEditFragmentViewModel
import com.example.notes.Data.Entities.Note
import com.example.notes.Data.Entities.ToDoListItem
import com.example.notes.Data.NotesRepository
import com.example.notes.Data.NotesViewModel
import com.example.notes.databinding.FragmentAddEditNoteBinding
import java.text.DateFormat
import java.util.*
import java.util.concurrent.Executors

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding
        get() = _binding!!

    private var noteId: Int = 0
    private lateinit var notesViewModel: AddEditFragmentViewModel

    private var toDoList = mutableListOf<ToDoListItem>()


    private var reminderDate: String? = null

    private var _toDoItemMap = mutableMapOf<Int, LinearLayout>()
    private var toDoItemMap: Map<Int, LinearLayout> = _toDoItemMap

    private var toDoItemId = 0

    private val lParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    private lateinit var noteBody: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        noteId = arguments?.getInt(ARG_NOTE_ID) ?: INVALID_ID
        val factory = VMFactory().initNotesAddEditScreenVMFactory(noteId)
        notesViewModel = ViewModelProvider(this, factory)[AddEditFragmentViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddEditNoteBinding.bind(view)



//        val note = getNoteId()
//        if (note != null) {
//            binding.apply {
//                textEditTitle.setText(note.title)
//                notetextEd.setText(note.text)
//                noteBody = noteBodyLayout
//            }
//            reminderDate = note.reminderDate
////            notesViewModel.getToDoListForNote(note.noteId)
////                .observe(viewLifecycleOwner, Observer { list ->
////                    for (item in list) {
////                        createToDoListItem(item.itemId, item.text, item.noteId, item.idInList)
////                    }
////                })
//        }

        notesViewModel.currentNoteLiveData.observe(viewLifecycleOwner) {
            binding.notetextEd.setText(it?.text ?: "")
            binding.textEditTitle.setText(it?.title ?: "")
        }

        notesViewModel.isInputError.observe(viewLifecycleOwner){
            if (it) Toast.makeText(requireContext(), "Not correct", Toast.LENGTH_LONG).show()
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
                //saveToDoList()
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
                saveNote()
//                createToDoListItem(0, "", noteId, toDoItemId)
//                showToDoList()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        if (noteId != -1) {
            val builder = AlertDialog.Builder(requireContext())
            builder
                .setTitle(getString(ALERT_DIALOG_TITLE_RES))
                .setMessage(getString(ALERT_DIALOG_MESSAGE_RES))
                .setPositiveButton(getString(DEL_BUTTON_TXT_RES)) { _, _ ->
                    notesViewModel.deleteNote(noteId)
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
            notesViewModel.saveNote(noteId, text, title, null)
        }
    }

//    private fun createToDoListItem(itemId: Int, text: String, noteId: Int, listId: Int) {
//        Log.d("SaveList", "create")
//        lParams.gravity = Gravity.LEFT
//        val itemContainer = LinearLayout(requireContext())
//        val checkBox = CheckBox(requireContext())
//        checkBox.setPadding(0, 100, 0, 0)
//        val editText = EditText(requireContext())
//        val buttonDelete = ImageButton(requireContext())
//        buttonDelete.setImageResource(R.drawable.ic_baseline_delete_24)
//
//
//        editText.setText(text)
//        buttonDelete.id = listId
//
//        itemContainer.addView(checkBox, lParams)
//        itemContainer.addView(editText, lParams)
//        itemContainer.addView(buttonDelete, lParams)
//
//        val listItem = ToDoListItem(
//            itemId = itemId,
//            noteId = noteId,
//            idInList = listId,
//            text = text
//        )
//
//        toDoList.add(listItem)
//
//        buttonDelete.setOnClickListener {
//            deleteToDoListItem(buttonDelete.id, listItem)
//        }
//        _toDoItemMap[toDoItemId] = itemContainer
//        toDoItemId++
//    }
//
//    private fun saveToDoList() {
//        if (toDoList.size != 0) {
//            var list = listOf<ToDoListItem>()
//            notesViewModel.getToDoListForNote(noteId)
//                .observe(viewLifecycleOwner, Observer { list = it })
//
//            if (list.isNotEmpty()) {
//                for (item in list) {
//                    for (newItem in toDoList) {
//                        if (item.itemId == newItem.itemId) {
//                            notesViewModel.updateToDoListItem(newItem)
//                        } else {
//                            notesViewModel.addToDoListItem(newItem)
//                        }
//                    }
//                }
//            } else {
//                for (item in toDoList) {
//                    notesViewModel.addToDoListItem(item)
//                }
//            }
//        }
//    }
//
//    private fun deleteToDoListItem(itemId: Int, listItem: ToDoListItem) {
//        noteBody.removeView(toDoItemMap.getValue(itemId))
//        _toDoItemMap.remove(itemId)
//        toDoItemMap = _toDoItemMap
//        notesViewModel.deleteToDoItem(listItem)
//
//    }
//
//    private fun showToDoList() {
//        Executors.newSingleThreadExecutor().execute {
//            requireActivity().runOnUiThread {
//                noteBody.removeAllViews()
//            }
//            for (item in toDoItemMap) {
//                requireActivity().runOnUiThread {
//                    noteBody.addView(item.value, lParams)
//                }
//            }
//        }
//
//        saveToDoList()
//    }

    private fun share() {

    }

    private fun changeTheme() {

    }

    //private fun getNoteId() = requireArguments().getInt(ARG_NOTE_ID)


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