package com.example.listtodo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.listtodo.model.ListToDoDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ListToDoDAO {

    @Query("SELECT * FROM ListToDoDataModel")
    fun getAllLists(): Flow<List<ListToDoDataModel>>

    @Query("SELECT * FROM ListToDoDataModel WHERE id = :listId")
    fun searchAllLists(listId: Long): Flow<ListToDoDataModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun saveList(listToDo: ListToDoDataModel)

    @Update
    fun updateList(listToDo: ListToDoDataModel)

    @Delete
    fun remove(listToDo: ListToDoDataModel)
}