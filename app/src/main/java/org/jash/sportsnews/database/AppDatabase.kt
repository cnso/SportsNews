package org.jash.sportsnews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.jash.sportsnews.dao.CategoryDao
import org.jash.sportsnews.dao.RecordDao
import org.jash.sportsnews.model.Category
import org.jash.sportsnews.model.Record

@Database(entities = [ Record::class, Category::class ], version = 1)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getCategoryDao():CategoryDao
    abstract fun getRecordDao(): RecordDao
}