package com.toan.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDo::class], version = 1)
@TypeConverters(Conveters::class)
abstract class ToDoDatabase : RoomDatabase() {
    companion object {
        const val NAME = "ToDo_DB"
    }

    abstract fun getToDoDao() : ToDoDAO
}