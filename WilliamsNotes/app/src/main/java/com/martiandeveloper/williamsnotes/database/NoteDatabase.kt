package com.martiandeveloper.williamsnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.martiandeveloper.williamsnotes.model.Note
import com.martiandeveloper.williamsnotes.utils.DateConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao?

    companion object {

        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {

            if (INSTANCE == null) {

                synchronized(RoomDatabase::class) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "williamsnotes.db"
                    ).build()

                }

            }

            return INSTANCE

        }

    }

}
