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
import org.jash.mylibrary.network.user
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
        binding.collectCheck.setOnClickListener{
            service.collect(id).observeForever {
                if (it.code == 0) {
                    processor.onNext(it.data)
                } else {
                    processor.onNext(it.msg)
                }
            }
        }
        database.getRecordDao().find(id).observe(this) {
            title = it.title
            binding.record = it
        }
        service.getCollectAll().observe(this) {
            if (it.code==0) {
                val collect = it.data.filter { map -> map["nid"] == id.toString() }
                if(user != null) {
                    detail.collect = collect.filter { map -> map["uid"] == user!!.id.toString() }.isNotEmpty()
                }
                detail.collectNumber = collect.size
            } else {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
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
        binding.comment.setOnClickListener {
            val a = IntArray(2)
            binding.list.getLocationOnScreen(a)
            val y = a[1]
            binding.scroll.getLocationOnScreen(a)
            val sy = a[1]
            binding.scroll.smoothScrollBy(0, y - sy)

        }
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
