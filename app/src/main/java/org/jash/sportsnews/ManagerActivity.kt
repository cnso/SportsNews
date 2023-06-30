package org.jash.sportsnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
//import com.alibaba.android.arouter.facade.annotation.Route
import org.jash.sportsnews.adapter.ManagerAdapter
import org.jash.sportsnews.databinding.ActivityManagerBinding
import org.jash.mylibrary.model.Category
import java.util.Date
//@Route(path = "/news/manager")
class ManagerActivity : AppCompatActivity() {
    lateinit var binding:ActivityManagerBinding
    lateinit var adapter: ManagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityManagerBinding>(this, R.layout.activity_manager)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        title = "频道管理"
        val list = mutableListOf<Any?>()
        for (i in 1..30) {
            list.add(Category(Date(), i, "分类$i", "分类$i"))
        }
        list.add(0, "已添加频道")
        list.add(10, "推荐频道")

        adapter = ManagerAdapter(list)
        binding.adapter = adapter
        binding.edit.setOnClickListener {
            adapter.editable.set(adapter.editable.get() != true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}