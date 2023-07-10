package org.jash.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import org.jash.mylibrary.adapter.CommonAdapter
import org.jash.mylibrary.dao.UserDao
import org.jash.mylibrary.database.database
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.model.Comment
import org.jash.mylibrary.model.User
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.user
import org.jash.profile.databinding.ActivityUserListBinding
import org.jash.profile.model.UserFollow
import java.util.Date
import kotlin.concurrent.thread

class UserListActivity : AppCompatActivity() {
    var isFollows:Boolean = false
    lateinit var binding:ActivityUserListBinding
    val adapter = CommonAdapter(mapOf(UserFollow::class.java to (R.layout.user_item to BR.user)))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        isFollows = intent.getBooleanExtra("isFollows", false)
        binding.adapter = adapter
        setSupportActionBar(binding.toolbar)
        title = if (isFollows) "我的关注" else "我的粉丝"
        service.getFollowsAll().observe(this) {
            if (it.code == 0) {
                val list =
                    it.data.filter { map -> map[if (isFollows) "uid" else "fuid"] == user?.id?.toString() }

                val ids = it.data.filter { map -> map["uid"] == user?.id?.toString() }
                    .map { map -> map["fuid"]?.toInt() }

                adapter += list.map { map ->
                    val uid = map[if (!isFollows) "uid" else "fuid"]?.toInt()

                    UserFollow(ObservableBoolean(uid in ids)).apply {
                        loadUser(database.getUserDao(), uid ?: 0, user)
                    }
                }

//                database.getUserDao().getUserAll().observe(this) {
//                    adapter += it.map {
//                        UserFollow(ObservableBoolean(it.id in ids)).apply {
//                            user.set(it)
//                        }
//                    }
//                }
            } else {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun loadUser(userDao: UserDao, id:Int, field: ObservableField<User>) {
        userDao.getUserById(id).observe(this){ user ->
            if (user != null) {
                field.set(user)
            } else {
                service.getAllUser().observe(this) { res ->
                    if (res.code == 0) {
                        thread { userDao.insertAll(*res.data.toTypedArray()) }
                        val temp = res.data.find { d -> d.id == id } ?: User(Date(), -1, "", "", 0, 0, "用户已注销")
                        field.set(temp)
                    }
                }
            }
        }

    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }
}