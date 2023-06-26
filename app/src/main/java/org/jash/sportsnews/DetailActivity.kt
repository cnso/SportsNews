package org.jash.sportsnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import org.jash.sportsnews.database.database
import org.jash.sportsnews.databinding.ActivityDetailBinding

@Route(path = "/news/detail")
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getIntExtra("id", 0)
        database.getRecordDao().find(id).observe(this) {
            title = it.title
            binding.record = it
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