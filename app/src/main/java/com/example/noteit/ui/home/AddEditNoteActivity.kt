package com.example.noteit.ui.home

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import com.example.noteit.model.Note
import com.example.noteit.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEditNoteActivity : ComponentActivity() {

    private val viewModel: NoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val noteId = intent.getIntExtra("note_id", -1)
        val noteTitle = intent.getStringExtra("note_title") ?: ""
        val noteContent = intent.getStringExtra("note_content") ?: ""

        val existingNote: Note? = if (noteId != -1) {
            Note(id = noteId, title = noteTitle, content = noteContent)
        } else null

        val composeView = ComposeView(this).apply {
            setContent {
                MaterialTheme {
                    AddEditNoteScreen(
                        note = existingNote,
                        onSaveNote = { note ->
                            if (note.id != null) {
                                viewModel.updateNote(note)
                            } else {
                                viewModel.addNote(note.title, note.content)
                            }
                            setResult(Activity.RESULT_OK)
                            println("Hello ---- " + viewModel.notes.value)
                            finish()
                        },
                        onDeleteNote = { noteId ->
                            viewModel.deleteNote(noteId)
                            setResult(Activity.RESULT_OK)
                            finish()
                        },
                        onBack = { finish() }
                    )
                }
            }
        }
        setContentView(composeView)
    }
}
