package org.jash.sportsnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import org.jash.mylibrary.logging.logging
import org.jash.sportsnews.adapter.PageAdapter
import org.jash.sportsnews.database.database
import org.jash.sportsnews.databinding.ActivityNewsBinding
import org.jash.sportsnews.network.service
import org.jash.sportsnews.viewmodel.MainViewModel

@Route(path = "/news/main")
class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityNewsBinding>(this, R.layout.activity_news)
        setSupportActionBar(binding.searchBar)
        val adapter = PageAdapter(supportFragmentManager)
        binding.page.adapter = adapter
        binding.tab.setupWithViewPager(binding.page)
        val viewModel by viewModels<MainViewModel>()
        if (viewModel.categories == null) {
            viewModel.categories = service.getCategory()
        }
        viewModel.categories?.observe(this) {
            if (it.code == 0) {
                Thread {
                    it.data.forEach(database.getCategoryDao()::insert)
                    runOnUiThread {
                        adapter.data = it.data
                    }
                }.start()

            } else {
                logging(it)
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
        binding.managerIcon.setOnClickListener {
            ARouter.getInstance()
                .build("/news/manager")
                .navigation()
        }

    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.news_menu, menu)
//        val item = menu.findItem(R.id.search)
//        val searchView = item.actionView as SearchView
//        searchView.queryHint = "搜索体育新闻"
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                logging("onQueryTextSubmit: $query")
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                logging("onQueryTextChange: $newText")
//                return false
//            }
//
//        })
//        return true
//    }
}