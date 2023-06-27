package org.jash.sportsnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import org.jash.community.CommunityFragment
import org.jash.mylibrary.logging.logging
import org.jash.profile.ProfileFragment
import org.jash.sportsnews.adapter.PageAdapter
import org.jash.sportsnews.database.database
import org.jash.sportsnews.databinding.ActivityNewsBinding
import org.jash.sportsnews.fragments.NewsFragment
import org.jash.sportsnews.network.service
import org.jash.sportsnews.viewmodel.MainViewModel
import org.jash.video.VideoFragment

@Route(path = "/news/main")
class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityNewsBinding>(this, R.layout.activity_news)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, NewsFragment.newInstance())
            .commit()
        binding.bottomMenu.setOnItemSelectedListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, when(it.itemId) {
                    R.id.b_news -> NewsFragment.newInstance()
                    R.id.b_video -> VideoFragment.newInstance()
                    R.id.b_community -> CommunityFragment.newInstance()
                    R.id.b_profile -> ProfileFragment.newInstance()
                    else -> NewsFragment.newInstance()
                })
                .commit()
            true
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