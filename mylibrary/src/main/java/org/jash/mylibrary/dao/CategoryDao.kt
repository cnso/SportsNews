package org.jash.mylibrary.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.jash.mylibrary.model.Category
@Dao
interface CategoryDao {
    @Upsert
    fun insert(category: Category)
    @Query("select * from category where id = :id")
    fun find(id:Int):LiveData<Category>
}