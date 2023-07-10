package org.jash.sportsnews.util

import android.widget.Toast
import org.jash.mylibrary.dao.UserDao
import org.jash.mylibrary.database.database
import org.jash.mylibrary.model.Comment
import org.jash.mylibrary.model.User
import org.jash.mylibrary.network.service
import java.util.Date
import kotlin.concurrent.thread

fun commentLoadUser(comments:List<Comment>, userDao: UserDao) {
    comments.forEach { comment ->
        userDao.getUserById(comment.uid).observeForever{ user ->
            if (user != null) {
                comment.user!!.set(user)
            } else {
                service.getAllUser().observeForever { res ->
                    if (res.code == 0) {
                        thread { userDao.insertAll(*res.data.toTypedArray()) }
                        val temp = res.data.find { d -> d.id == comment.uid } ?: User( Date(), -1, "", "", 0, 0, "用户已注销")
                        comment.user!!.set(temp)
                    }
                }
            }
        }
        if(comment.replays?.isEmpty() == false) {
            commentLoadUser(comment.replays!!, userDao)
        }
    }
}