package org.jash.sportsnews

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.jash.mylibrary.activity.SafeSubscribe
import org.jash.mylibrary.adapter.CommonAdapter
//import com.alibaba.android.arouter.facade.annotation.Route
import org.jash.mylibrary.database.database
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.model.Comment
import org.jash.mylibrary.model.User
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.token
import org.jash.mylibrary.processor
import org.jash.profile.LoginActivity
import org.jash.sportsnews.databinding.ActivityDetailBinding
import org.jash.sportsnews.model.Detail
import org.jash.sportsnews.util.commentLoadUser
import java.util.Date
import kotlin.concurrent.thread

//@Route(path = "/news/detail")
class DetailActivity : AppCompatActivity() {
    lateinit var adapter:CommonAdapter<Comment>
    lateinit var detail:Detail
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
        detail = Detail(id)
        binding.detail = detail
        database.getRecordDao().find(id).observe(this) {
            title = it.title
            binding.record = it
        }
        loadComment(id)
        SafeSubscribe(
            processor.ofType(Comment::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    detail.parent = it
                },
            processor.ofType(String::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
        )

        binding.commentEdit.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.commentEdit.onEditorAction(EditorInfo.IME_ACTION_DONE)
        binding.commentEdit.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if(token != null) {
                        service.savaComment(mapOf("nid" to id.toString(), "parentid" to (detail.parent?.id ?: 0).toString(), "content" to detail.comment))
                            .observe(this) {
                                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                                if(it.code == 0 ){
                                    adapter.clear()
                                    loadComment(id)
                                    detail.comment = ""
                                    detail.parent = null
                                    binding.commentEdit.clearFocus()
                                    val manager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                                    if (manager != null)
                                        manager.hideSoftInputFromWindow(binding.commentEdit.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                                }
                            }
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    true
                }
                else -> false
            }
        }
//        if (field.get() == null) {
//            field.set(User(null, Date(), -1, "https://gimg3.baidu.com/search/src=http%3A%2F%2Fpics0.baidu.com%2Ffeed%2F8435e5dde71190efdb1dbeef9327fa1afcfa60e0.jpeg%40f_auto%3Ftoken%3D443709dfe069ab08615bd31532484e30&refer=http%3A%2F%2Fwww.baidu.com&app=2021&size=f360,240&n=0&g=0n&q=75&fmt=auto?sec=1688230800&t=a6cebd8cad163cc3c8877f8a90a2af3a", "", "", 0, -1, "加载中"))
//        }

    }
    fun loadComment(id:Int) {
        service.getCommentByNid(id).observe(this) {
            if (it.code == 0) {
                commentLoadUser(it.data, database.getUserDao())
                adapter += it.data
                detail.commentNumber = it.data.size
            } else {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
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
