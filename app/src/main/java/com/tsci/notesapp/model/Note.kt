package com.tsci.notesapp.model

import java.util.*

data class Note(
    val id: Long = 0,
    val note: String,
    val date: Date
)