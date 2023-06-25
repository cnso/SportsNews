package org.jash.sportsnews.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class TypeConverter {
    private val sdf = SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
    @TypeConverter
    fun fromCreateTime(date: Date) = sdf.format(date)
    @TypeConverter
    fun toCreateTime(src:String) = sdf.parse(src)
}