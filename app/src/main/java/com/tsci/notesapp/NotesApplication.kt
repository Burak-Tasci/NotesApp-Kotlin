package com.tsci.notesapp

import android.app.Application
import com.tsci.notesapp.data.NoteRoomDatabase

class NotesApplication : Application() {

    val database: NoteRoomDatabase by lazy { NoteRoomDatabase.getDatabase(this) }
}