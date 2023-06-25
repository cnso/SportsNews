package org.jash.sportsnews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.jash.sportsnews.dao.CategoryDao
import org.jash.sportsnews.model.Category

@Database(entities = [ Category::class ], version = 1)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getCategoryDao():CategoryDao
}