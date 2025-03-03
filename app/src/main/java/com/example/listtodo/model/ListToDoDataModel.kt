package com.example.listtodo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListToDoDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tituloLista: String,
    val descricaoLista: String,
    val concluida: Boolean
)