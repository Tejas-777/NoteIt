package com.example.noteit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteit.data.NoteRepository
import com.example.noteit.model.Note

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        loadNotes()
    }

    fun loadNotes() {
        _notes.value = repository.getAllNotes()
    }

    fun addNote(title: String, content: String) {
        repository.addNote(title, content)
        loadNotes()
    }

    fun updateNote(note: Note) {
        repository.updateNote(note)
        loadNotes()
    }

    fun deleteNote(id: Int) {
        repository.deleteNote(id)
        loadNotes()
    }
}

