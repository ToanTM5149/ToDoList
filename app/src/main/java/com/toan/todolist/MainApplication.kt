package com.toan.todolist

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.toan.todolist.database.ToDoDatabase

class MainApplication : Application() {
    companion object {
        lateinit var toDoDatabase: ToDoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        toDoDatabase = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            ToDoDatabase.NAME
        ).build()
    }
}