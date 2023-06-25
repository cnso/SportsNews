package org.jash.sportsnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Record(
    val content: String,
    val createTime: Date,
    val flag: Int,
    @PrimaryKey
    val id: Int,
    val imgurl: String,
    val looks: Int,
    val ntid: Int,
    val suid: Int,
    val title: String
)