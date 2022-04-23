package com.example.composetodoapp_myattempt5.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val text: String?,
    val isDone: Boolean,
    @PrimaryKey val id: Int? = null
)
