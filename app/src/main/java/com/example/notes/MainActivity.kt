package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.notes.Data.Note
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigator {

    private val canNavigate: Boolean
        get() = supportFragmentManager.backStackEntryCount > 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_container, NotesListFragment.newInstance())
                .commit()
        }

        setSupportActionBar(binding.toolbar)

        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(canNavigate)
            binding.findNoteField.isVisible = !canNavigate
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (canNavigate) {
            supportFragmentManager.popBackStack()
            true
        } else {
            finish()
            false
        }
    }

    override fun navigateToAddEditNoteScreen(note: Note?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, AddEditNoteFragment.newInstance(note))
            .addToBackStack(null)
            .commit()
    }
}