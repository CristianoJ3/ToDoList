package com.example.listtodo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListToDoDataModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val listTitle: String,
    val listDescription: String,
    val finished: Boolean,
    val imageBackground: Int
)