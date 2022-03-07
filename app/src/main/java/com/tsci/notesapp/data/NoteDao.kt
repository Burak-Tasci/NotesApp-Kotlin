package com.tsci.notesapp.data

import androidx.room.*
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
    fun getNote(id: Long): Flow<Note>

    @Query("SELECT * from Note ORDER BY note_date DESC")
    fun getAllNotes(): Flow<List<Note>>
}