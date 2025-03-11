package com.example.listtodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.listtodo.dao.ListToDoDAO
import com.example.listtodo.model.ListToDoDataModel

@Database(entities = [ListToDoDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun listToDoDAO(): ListToDoDAO

    companion object {
        @Volatile
        private var db: AppDatabase? = null

        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "toDoList.db"
            ).allowMainThreadQueries()
                .build().also {
                    db = it
                }
        }

    }

}