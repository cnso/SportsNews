package org.jash.sportsnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.jash.mylibrary.adapter.CommonAdapter
//import com.alibaba.android.arouter.facade.annotation.Route
import org.jash.mylibrary.database.database
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.model.Comment
import org.jash.mylibrary.model.User
import org.jash.mylibrary.network.service
import org.jash.sportsnews.databinding.ActivityDetailBinding
import java.util.Date
import kotlin.concurrent.thread

//@Route(path = "/news/detail")
class DetailActivity : AppCompatActivity() {
    lateinit var adapter:CommonAdapter<Comment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = CommonAdapter(mapOf(Comment::class.java to (R.layout.comment_item to BR.comment)))
        binding.adapter = adapter
        val id = intent.getIntExtra("id", 0)
        database.getRecordDao().find(id).observe(this) {
            title = it.title
            binding.record = it
        }
        service.getCommentByNid(id).observe(this) {
            if (it.code == 0) {
                it.data.forEach { comment ->
                    database.getUserDao().getUserById(comment.uid).observe(this) { user ->
                        if (user != null) {
                            comment.user!!.set(user)
                        } else {
                            service.getAllUser().observe(this) { res ->
                                if (res.code == 0) {
                                    thread { database.getUserDao().insertAll(*res.data.toTypedArray()) }
                                    val temp = res.data.find { d -> d.id == comment.uid } ?: User(null, Date(), -1, "", "", 0, 0, "用户已注销")
                                    comment.user!!.set(temp)
                                } else {
                                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                adapter += it.data
            } else {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
//        if (field.get() == null) {
//            field.set(User(null, Date(), -1, "https://gimg3.baidu.com/search/src=http%3A%2F%2Fpics0.baidu.com%2Ffeed%2F8435e5dde71190efdb1dbeef9327fa1afcfa60e0.jpeg%40f_auto%3Ftoken%3D443709dfe069ab08615bd31532484e30&refer=http%3A%2F%2Fwww.baidu.com&app=2021&size=f360,240&n=0&g=0n&q=75&fmt=auto?sec=1688230800&t=a6cebd8cad163cc3c8877f8a90a2af3a", "", "", 0, -1, "加载中"))
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
