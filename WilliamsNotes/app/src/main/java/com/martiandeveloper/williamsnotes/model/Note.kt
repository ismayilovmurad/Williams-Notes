package com.martiandeveloper.williamsnotes.model

import com.martiandeveloper.williamsnotes.utils.NEW_NOTE_ID
import java.util.*

data class Note(var id: Int, var date: Date, var text: String) {
    constructor() : this(NEW_NOTE_ID, Date(), "")
    constructor(date: Date, text: String) : this(NEW_NOTE_ID, date, text)
}
