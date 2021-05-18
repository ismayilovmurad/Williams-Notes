package com.martiandeveloper.williamsnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.martiandeveloper.williamsnotes.database.NoteDatabase
import com.martiandeveloper.williamsnotes.model.Note
import com.martiandeveloper.williamsnotes.utils.NEW_NOTE_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditViewModel(app: Application) : AndroidViewModel(app) {

    private val database = NoteDatabase.getInstance(app)
    val currentNote = MutableLiveData<Note>()

    fun getNoteById(noteId: Int) {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val note =
                    if (noteId != NEW_NOTE_ID) database?.noteDao()?.getNoteById(noteId) else Note()
                currentNote.postValue(note)
            }

        }

    }

    fun updateNote() {

        currentNote.value?.let {

            it.text = it.text.trim()

            if (it.id == NEW_NOTE_ID && it.text.isEmpty()) return

            viewModelScope.launch {

                withContext(Dispatchers.IO) {
                    if (it.text.isEmpty()) database?.noteDao()
                        ?.deleteNote(it) else database?.noteDao()?.insertNote(it)
                }

            }

        }

    }

}
