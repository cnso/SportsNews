package org.jash.mylibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity
data class User(
    val birthday: String?,
    val createTime: Date,
    @PrimaryKey
    val id: Int,
    val info: String?,
    val phone: String?,
    val sex: Int?,
    val uid: Int,
    val username: String
) {
    val imgurl
        get() = "https://cdn7.axureshop.com/demo/1852265/images/%E6%96%B0%E9%97%BB%E8%AF%A6%E6%83%85%EF%BC%88%E8%A7%86%E9%A2%91_%E6%96%87%E7%AB%A0%EF%BC%89/u1850.png"
}