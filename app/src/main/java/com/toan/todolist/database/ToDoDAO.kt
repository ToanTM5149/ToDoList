package com.toan.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM TODO")
    fun getAllToDo() : LiveData<List<ToDo>>

    @Insert
    fun addToDo(toDo : ToDo)

    @Query("DELETE FROM TODO WHERE ID = :id")
    fun deleteToDo(id : Int)

    @Update
    fun updateToDo(toDo : ToDo)
}