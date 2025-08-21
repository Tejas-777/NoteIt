package com.example.noteit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteit.databinding.ActivityMainBinding
import com.example.noteit.ui.home.AddEditNoteActivity
import com.example.noteit.ui.home.NoteAdapter
import com.example.noteit.viewmodel.NoteViewModel
import com.example.noteit.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(applicationContext)
    }


    private val addEditNoteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                viewModel.loadNotes()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupFab()
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(emptyList (), onNoteClick = { note ->
            val intent = Intent(this, AddEditNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
                putExtra("note_title", note.title)
                putExtra("note_content", note.content)
            }
            addEditNoteLauncher.launch(intent)
        })
        binding.recyclerNotes.layoutManager = LinearLayoutManager(this)
        binding.recyclerNotes.adapter = noteAdapter
    }

    private fun setupObservers() {
        viewModel.notes.observe(this) { notes ->
            noteAdapter.updateNotes(notes)
        }
    }

    private fun setupFab() {
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            addEditNoteLauncher.launch(intent)
        }
    }
}
