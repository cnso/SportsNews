package org.jash.sportsnews

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableMap
//import com.alibaba.android.arouter.facade.annotation.Route
//import com.alibaba.android.arouter.launcher.ARouter
import org.jash.community.CommunityFragment
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.setListener
import org.jash.profile.ProfileFragment
import org.jash.sportsnews.adapter.PageAdapter
import org.jash.mylibrary.database.database
import org.jash.sportsnews.databinding.ActivityNewsBinding
import org.jash.sportsnews.fragments.NewsFragment
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.token
import org.jash.profile.LoginActivity
import org.jash.sportsnews.viewmodel.MainViewModel
import org.jash.video.VideoFragment

//@Route(path = "/news/main")
class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityNewsBinding>(this, R.layout.activity_news)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, NewsFragment.newInstance())
            .commit()
        binding.bottomMenu.setOnItemSelectedListener {
            if(token == null && it.itemId in listOf(R.id.b_community, R.id.b_profile)) {
                startActivity(Intent(this, LoginActivity::class.java))
//                ARouter.getInstance()
//                    .build("/profile/login")
//                    .navigation()
                return@setOnItemSelectedListener false
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main, when(it.itemId) {
                        R.id.b_news -> NewsFragment.newInstance()
                        R.id.b_video -> VideoFragment.newInstance()
                        R.id.b_community -> CommunityFragment.newInstance()
                        R.id.b_profile -> ProfileFragment.newInstance()
                        else -> NewsFragment.newInstance()
                    })
                    .commit()
            }

            true
        }
        binding.fab.setOnClickListener {
            if (binding.fabEdit.isVisible) {
                binding.fabEdit.startAnimation(AnimationUtils.loadAnimation(this, R.anim.exit).apply {
                    setListener(end = {binding.fabEdit.isVisible = false})
                })
                binding.fabVideo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.exit).apply {
                    setListener(end = {binding.fabVideo.isVisible = false})
                })
                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 45f, 0f)
                    .setDuration(300)
                    .start()
            } else {
                binding.fabEdit.startAnimation(AnimationUtils.loadAnimation(this, R.anim.enter).apply {
                    setListener(start = {binding.fabEdit.isVisible = true}, end = {
                        binding.fabVideo.startAnimation(AnimationUtils.loadAnimation(this@NewsActivity, R.anim.enter).apply {
                            setListener(start = {binding.fabVideo.isVisible = true})
                        })
                    })
                })
                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 0f, 45f)
                    .setDuration(300)
                    .start()
            }
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