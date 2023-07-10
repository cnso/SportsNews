package org.jash.mylibrary.model

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity
data class User(

    val createTime: Date,
    @PrimaryKey
    val id: Int,
    var info: String?,
    val phone: String?,
    var sex: Int?,
    val uid: Int,
    val username: String
) : BaseObservable() {

    var birthday: String? = null
        get() = field ?: "完善生日信息"
    val imgurl
        get() = "https://cdn7.axureshop.com/demo/1852265/images/%E6%96%B0%E9%97%BB%E8%AF%A6%E6%83%85%EF%BC%88%E8%A7%86%E9%A2%91_%E6%96%87%E7%AB%A0%EF%BC%89/u1850.png"
    var gender
        get() = when(sex) {
            0 -> "男"
            1 -> "女"
            else -> "未知"
        }
        set(value) {
            sex = when(value) {
                "男" -> 0
                "女" -> 1
                else -> null
            }
        }


}