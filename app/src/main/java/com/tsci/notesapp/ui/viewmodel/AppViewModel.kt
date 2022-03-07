package com.tsci.notesapp

import android.content.ClipData
import androidx.lifecycle.*
import com.tsci.notesapp.data.NoteDao
import com.tsci.notesapp.data.Note
import kotlinx.coroutines.launch
import java.util.*

class AppViewModel(private val noteDao: NoteDao) : ViewModel() {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes().asLiveData()

    private fun getUpdatedNoteEntry(
        Id: Long,
        noteText: String,
        noteDate: Date
    ): Note {
        return Note(
            id = Id,
            noteText = noteText,
            noteDate = noteDate
        )
    }

    fun updateNote(
        Id: Long,
        noteText: String,
        noteDate: Date
    ) {
        val updatedNote = getUpdatedNoteEntry(Id, noteText, noteDate)
        updateNote(updatedNote)
    }



    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }


    private fun updateNote(note: Note){
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    private fun insertNote(note: Note){
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }
    private fun getNewNoteEntry(noteText: String,
                                noteDate: Date): Note {
        return Note(
            noteText = noteText,
            noteDate = noteDate
        )
    }
    fun addNewNote(noteText: String,
                   noteDate: Date) {
        val newNote = getNewNoteEntry(noteText, noteDate)
        insertNote(newNote)
    }
    fun retrieveNote(id: Long): LiveData<Note>{
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
