package com.toan.todolist.database

import androidx.room.TypeConverter
import java.util.Date

class Conveters {

    @TypeConverter
    fun fromDate(date : Date) : Long{
        return  date.time
    }

    @TypeConverter
    fun toDate(time : Long) : Date{
        return Date(time)
    }
}