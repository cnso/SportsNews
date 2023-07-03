package org.jash.mylibrary.model

import androidx.databinding.ObservableField
import org.jash.mylibrary.processor
import java.text.SimpleDateFormat
import java.util.Date

data class Comment(
    val content: String,
    val createTime: Date,
    val id: Int,
    val nid: Int,
    val parentid: Int,
    val uid: Int,
    val replays: List<Comment>?
) {
    var user: ObservableField<User>? = null
        get() {
            if (field == null) {
                field = ObservableField()
            }
            return field
        }
    val timeString: String
        get() {
            return SimpleDateFormat("yyyy-MM-dd").format(createTime)
        }
    fun replay() {
        processor.onNext(this)
    }
}