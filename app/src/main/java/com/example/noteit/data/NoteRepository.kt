package com.example.noteit.data

import android.content.Context
import android.content.SharedPreferences
import com.example.noteit.model.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NoteRepository(context: Context) {

    companion object {
        private const val PREF_NAME = "notes_pref"
        private const val NOTES_KEY = "notes_key"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()
    private var noteList: MutableList<Note> = loadNotes()

    private fun loadNotes(): MutableList<Note> {
        val json = sharedPreferences.getString(NOTES_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Note>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    private fun saveNotes() {
        val json = gson.toJson(noteList)
        sharedPreferences.edit().putString(NOTES_KEY, json).apply()
    }

    fun getAllNotes(): List<Note> {
        noteList = loadNotes()
        return noteList
    }

    fun addNote(title: String, content: String) {
        val newId = if (noteList.isEmpty()) 1 else noteList.maxOf { it.id ?: 0 } + 1
        val newNote = Note(id = newId, title = title, content = content)
        noteList.add(newNote)
        saveNotes()
    }

    fun updateNote(updatedNote: Note) {
        val index = noteList.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            noteList[index] = updatedNote
            saveNotes()
        }
    }

    fun deleteNote(noteId: Int) {
        noteList.removeAll { it.id == noteId }
        saveNotes()
    }

}
