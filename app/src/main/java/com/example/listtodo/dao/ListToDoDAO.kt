package com.example.listtodo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.listtodo.model.ListToDoDataModel

@Dao
interface ListToDoDAO {

    @Query("SELECT * FROM ListToDoDataModel")
    fun getAllLists(): List<ListToDoDataModel>

    @Query("SELECT * FROM ListToDoDataModel WHERE id = :listId")
    fun searchAllLists(listId: String): ListToDoDataModel?

    @Insert
    fun saveList(listToDo: ListToDoDataModel)

    @Delete
    fun remove(listToDo: ListToDoDataModel)
}