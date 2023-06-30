package org.jash.mylibrary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.jash.mylibrary.dao.CategoryDao
import org.jash.mylibrary.dao.RecordDao
import org.jash.mylibrary.dao.UserDao
import org.jash.mylibrary.model.Category
import org.jash.mylibrary.model.Record
import org.jash.mylibrary.model.User

@Database(entities = [ Record::class, Category::class, User::class ], version = 1)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getRecordDao(): RecordDao
    abstract fun getUserDao(): UserDao
}