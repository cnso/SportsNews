package org.jash.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableMap
//import com.alibaba.android.arouter.facade.annotation.Route
import org.jash.mylibrary.network.service
import org.jash.profile.databinding.ActivityRegistryBinding

//@Route(path = "/profile/registry")
class RegistryActivity : AppCompatActivity() {
    val time:ObservableInt = ObservableInt(0)
    val body:ObservableMap<String, String> = ObservableArrayMap<String, String>().apply {
        this["phone"] = ""
        this["code"] = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRegistryBinding>(this, R.layout.activity_registry)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        binding.time = time
        binding.body = body
        binding.send.setOnClickListener {
            if(body["phone"].isNullOrEmpty() || body["phone"]?.length != 11) {
                Toast.makeText(this, "电话不合法", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val timer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    time.set((millisUntilFinished / 1000).toInt())
                }

                override fun onFinish() {
                    time.set(0)
                }
            }
            timer.start()
            service.getRegistryCode(body["phone"] ?: "").observe(this) {
                if(it.code == 0) {
                    body["code"] = it.data.toString()
                } else {
                    timer.cancel()
                    time.set(0)
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.verify.setOnClickListener { 
            if (body["phone"].isNullOrEmpty() || body["code"].isNullOrEmpty()) {
                Toast.makeText(this, "电话和验证码不能为空", Toast.LENGTH_SHORT).show()
            } else {
                service.checkRegistryCode(body).observe(this) {
                    if (it.code == 0) {
                        Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, RegistryActivity2::class.java).apply {
                            putExtra("phone", body["phone"])
                        })
                    } else {
                        Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}