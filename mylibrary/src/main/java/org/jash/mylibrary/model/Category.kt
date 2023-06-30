package org.jash.mylibrary.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Category(
    val createTime: Date,
    @PrimaryKey
    val id: Int,
    val info: String,
    val name: String
)