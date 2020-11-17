package com.martiandeveloper.williamsnotes.utils

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

}
