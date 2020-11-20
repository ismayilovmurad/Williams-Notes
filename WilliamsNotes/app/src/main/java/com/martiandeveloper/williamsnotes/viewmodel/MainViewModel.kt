package com.martiandeveloper.williamsnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.martiandeveloper.williamsnotes.database.NoteDatabase

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = NoteDatabase.getInstance(app)

    val noteList = database?.noteDao()?.getAll()

}
