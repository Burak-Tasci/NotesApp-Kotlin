package com.tsci.notesapp.database

import android.content.ClipData
import androidx.room.*
import com.tsci.notesapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNote(id: Int): Flow<Note>

    @Query("SELECT * from Note ORDER BY date DESC")
    fun getIAllNotes(): Flow<List<Note>>
}