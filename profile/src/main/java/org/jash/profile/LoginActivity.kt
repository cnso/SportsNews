package org.jash.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableMap
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.token
import org.jash.mylibrary.network.user
//import com.alibaba.android.arouter.facade.annotation.Route
//import com.alibaba.android.arouter.launcher.ARouter
import org.jash.profile.databinding.ActivityLoginBinding

//@Route(path = "/profile/login")
class LoginActivity : AppCompatActivity() {
    val body:ObservableMap<String, String> = ObservableArrayMap<String, String>().apply {
        this["name"] = ""
        this["password"] = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        binding.registry.setOnClickListener {
//            ARouter.getInstance().build("/profile/registry")
//                .navigation()
            startActivity(Intent(this,RegistryActivity::class.java))
        }
        service.getUserDetail().observe(this) {
            logging(it)
        }
        binding.body = body
        binding.login.setOnClickListener {
            if (body["name"].isNullOrEmpty() || body["password"].isNullOrEmpty()) {
                Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show()
            } else {
                service.login(body).observe(this) {
                    it.takeIf { it.code == 0 }?.run {
                        token = data
                        getSharedPreferences("first", MODE_PRIVATE)
                            .edit().putString("token", token)
                            .apply()
                        service.getUserDetail().observe(this@LoginActivity) {
                            logging(it)
                        }
                        service.getMyDetail().observe(this@LoginActivity) {
                            if (it.code == 0) {
                                user = it.data
                            } else {
                                Toast.makeText(this@LoginActivity, it.msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                        finish()
                    }
                    it.takeIf { it.code != 0 }?.run {
                        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}