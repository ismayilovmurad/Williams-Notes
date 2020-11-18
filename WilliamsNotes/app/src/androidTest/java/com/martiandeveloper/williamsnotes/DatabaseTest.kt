package com.martiandeveloper.williamsnotes

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.martiandeveloper.williamsnotes.database.NoteDao
import com.martiandeveloper.williamsnotes.database.NoteDatabase
import com.martiandeveloper.williamsnotes.model.Note
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: NoteDao
    private lateinit var database: NoteDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, NoteDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = database.noteDao()!!
    }

    @Test
    fun createNotes() {
        val list = ArrayList<Note>()
        list.add(Note(1, Date(), "Watch a LinkedIn tutorial"))
        list.add(Note(2, Date(), "Continue to build Movie Diary app"))

        dao.insertAll(list)

        val count = dao.getCount()
        assertEquals(count, list.size)
    }

    @Test
    fun insertNote() {
        val note = Note()
        note.text = "Do the workout"

        dao.insertNote(note)

        val savedNote = dao.getNoteById(1)

        assertEquals(savedNote?.id ?: 0, 1)
    }

    @After
    fun closeDb() {
        database.close()
    }

}
