package com.martiandeveloper.williamsnotes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martiandeveloper.williamsnotes.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(notes: List<Note>)

    @Query("SELECT * FROM notes ORDER BY date ASC")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Note?

    @Query("SELECT COUNT(*) FROM notes")
    fun getCount(): Int

    @Delete
    fun deleteSelectedNotes(selectedNotes: List<Note>): Int

    @Query("DELETE FROM notes")
    fun deleteAllNotes(): Int

    @Delete
    fun deleteNote(note: Note)
}
