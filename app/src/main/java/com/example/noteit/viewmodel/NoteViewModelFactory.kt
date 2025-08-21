package com.example.noteit.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteit.data.NoteRepository

class NoteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = NoteRepository(context.applicationContext)
        return NoteViewModel(repository) as T
    }
}
