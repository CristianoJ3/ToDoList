package com.example.listtodo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.listtodo.model.ListToDoDataModel

@Dao
interface ListToDoDAO {

    @Insert
    suspend fun insertList(listToDo: ListToDoDataModel)

    @Delete
    suspend fun deletar(listToDo: ListToDoDataModel)

    @Query("SELECT * FROM ListToDoDataModel WHERE id = :listId")
    fun autentica(listId: String, senha: String): ListToDoDataModel?
}