package com.martiandeveloper.williamsnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.martiandeveloper.williamsnotes.database.NoteDatabase
import com.martiandeveloper.williamsnotes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = NoteDatabase.getInstance(app)

    val noteList = database?.noteDao()?.getAll()

    fun deleteSelectedNotes(selectedNotes: List<Note>) {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                database?.noteDao()?.deleteSelectedNotes(selectedNotes)
            }

        }

    }

    fun deleteAllNotes() {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                database?.noteDao()?.deleteAllNotes()
            }

        }

    }

}
