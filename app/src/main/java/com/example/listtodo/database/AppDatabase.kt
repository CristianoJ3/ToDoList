package com.example.listtodo.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.listtodo.dao.ListToDoDAO

abstract class AppDatabase : RoomDatabase(){

    abstract fun lisToDoDAO(): ListToDoDAO

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