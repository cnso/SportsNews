package org.jash.sportsnews

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = getSharedPreferences("first", MODE_PRIVATE)
        if (preferences.getBoolean("isFirst", true)) {
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.user_title)
                .setMessage(R.string.user_context)
                .setPositiveButton(R.string.agree) { _, _ ->
//                    startActivity(Intent(this, NewsActivity::class.java))
                    ARouter.getInstance().build("/news/main").navigation()
                    preferences.edit().putBoolean("isFirst", false).apply()
                    finish()
                }
                .setNegativeButton(R.string.leave) { _, _ ->
                    finishAffinity()
                }
                .create()
            dialog.show()
        } else {
            ARouter.getInstance().build("/news/main").navigation()
            finish()
//            startActivity(Intent(this, NewsActivity::class.java))
        }
    }
}