package com.tsci.notesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.tsci.notesapp.data.NoteDao
import com.tsci.notesapp.data.Note

class AppViewModel(private val noteDao: NoteDao) : ViewModel() {
    val allItems: LiveData<List<Note>> = noteDao.getAllNotes().asLiveData()


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