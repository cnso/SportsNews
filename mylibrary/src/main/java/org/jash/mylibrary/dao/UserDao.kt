package org.jash.mylibrary.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.jash.mylibrary.model.User

@Dao
interface UserDao {
    @Query("select * from user where id = :id")
    fun getUserById(id:Int):LiveData<User?>
    @Upsert
    fun insertAll(vararg user: User)
}