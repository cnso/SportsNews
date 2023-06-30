package org.jash.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableMap
import org.jash.mylibrary.network.service
import org.jash.profile.databinding.ActivityRegistry2Binding

class RegistryActivity2 : AppCompatActivity() {
    val body:ObservableMap<String, String> = ObservableArrayMap<String, String>().apply {
        this["username"] = ""
        this["password"] = ""
        this["phone"] = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRegistry2Binding>(this, R.layout.activity_registry2)
        body["phone"] = intent?.getStringExtra("phone")
        binding.body = body
        binding.registry.setOnClickListener {
            if (body["username"].isNullOrEmpty() || body["password"].isNullOrEmpty() || body["phone"].isNullOrEmpty()) {
                Toast.makeText(this, "用户名,密码和电话都不能为空", Toast.LENGTH_SHORT).show()
            } else {
                service.register(body).observe(this) {
                    if (it.code == 0) {
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}