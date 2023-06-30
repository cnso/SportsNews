package org.jash.mylibrary.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.jash.mylibrary.model.Category
import org.jash.mylibrary.model.Record

@Dao
interface RecordDao {
    @Upsert
    fun insert(record: Record)
    @Query("select * from record where id = :id")
    fun find(id:Int):LiveData<Record>
}