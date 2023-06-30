package org.jash.mylibrary.model

import android.content.Context
import android.content.Intent
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.alibaba.android.arouter.launcher.ARouter
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
) {
    fun showDetail(context:Context) {
        context.startActivity(Intent(context, Class.forName("org.jash.sportsnews.DetailActivity")).also {
            it.putExtra("id", id)
        })
//        ARouter.getInstance()
//            .build("/news/detail")
//            .withInt("id", id)
//            .navigation()
    }
}