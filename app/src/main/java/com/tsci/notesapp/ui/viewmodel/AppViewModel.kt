package com.tsci.notesapp.ui.viewmodel

import android.util.Log
import androidx.annotation.ColorRes
import androidx.lifecycle.*
import com.tsci.notesapp.data.NoteDao
import com.tsci.notesapp.data.Note
import kotlinx.coroutines.launch
import java.util.*

class AppViewModel(private val noteDao: NoteDao) : ViewModel() {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes().asLiveData()


    private fun getUpdatedNoteEntry(
        Id: Long,
        noteTitle: String,
        noteText: String,
        noteDate: Date
    ): Note {
        return Note(
            id = Id,
            noteTitle = noteTitle,
            noteText = noteText,
            noteDate = noteDate
        )
    }

    fun updateNote(
        Id: Long,
        noteTitle: String,
        noteText: String,
        noteDate: Date
    ) {
        val updatedNote = getUpdatedNoteEntry(Id, noteTitle, noteText, noteDate)
        updateNote(updatedNote)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    private fun getNewNoteEntry(
        noteTitle: String,
        noteText: String,
        noteDate: Date
    ): Note {
        return Note(
            noteTitle = noteTitle,
            noteText = noteText,
            noteDate = noteDate
        )
    }

    fun addNewNote(
        noteTitle: String,
        noteText: String,
        noteDate: Date
    ) {
        val newNote = getNewNoteEntry(noteTitle, noteText, noteDate)
        insertNote(newNote)
    }

    fun retrieveNote(id: Long): LiveData<Note> {
        return noteDao.getNote(id).asLiveData()
    }

}

class AppViewModelFactory(private val noteDao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
