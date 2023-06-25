package org.jash.sportsnews.model

import java.sql.Date

data class Record(
    val content: String,
    val createTime: Date,
    val flag: Int,
    val id: Int,
    val imgurl: String,
    val looks: Int,
    val ntid: Int,
    val suid: Int,
    val title: String
)