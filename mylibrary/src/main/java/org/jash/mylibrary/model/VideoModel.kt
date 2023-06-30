package org.jash.mylibrary.model

import java.util.Date

data class VideoModel(
    val createTime: Date,
    val flag: Int,
    val id: Int,
    val imgurl: String?,
    val info: String,
    val looks: Int,
    val suid: Int,
    val title: String,
    val type: Int,
    val videourl: String?
)