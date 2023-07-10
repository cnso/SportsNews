package org.jash.profile.model

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.token
import org.jash.mylibrary.network.user
import org.jash.profile.R
import org.jash.profile.databinding.ActivityUpdatePasswordBinding

class UpdatePasswordActivity : AppCompatActivity() {
    lateinit var binding:ActivityUpdatePasswordBinding
    val body = ObservableArrayMap<String, String>().apply {
        this["old_password"] = ""
        this["password"] = ""
        this["confirm"] = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_password)
        binding.body = body
        binding.password.editText?.addTextChangedListener {
            val password = body["password"] ?: ""
            if (password.length < 6) {
                binding.password.isErrorEnabled = true
                binding.password.error = "密码要大于6位"
            } else if ("\\d".toRegex().find(password) == null) {
                binding.password.isErrorEnabled = true
                binding.password.error = "至少包含一位数字"
            } else if ("[a-z]".toRegex().find(password) == null) {
                binding.password.isErrorEnabled = true
                binding.password.error = "至少包含一位小写字母"
            } else if ("[A-Z]".toRegex().find(password) == null) {
                binding.password.isErrorEnabled = true
                binding.password.error = "至少包含一位大写字母"
            } else {
                binding.password.isErrorEnabled = false
            }
        }
        binding.confirm.editText?.addTextChangedListener {
            if (body["confirm"] == body["password"]) {
                binding.confirm.isErrorEnabled = false
            } else {
                binding.confirm.isErrorEnabled = true
                binding.confirm.error = "两次密码不一致"
            }
        }
        binding.commint.setOnClickListener {
            if (!body["password"].isNullOrEmpty() && !body["confirm"].isNullOrEmpty() && !binding.password.isErrorEnabled && !binding.confirm.isErrorEnabled) {
                service.updatePassword(body["password"] ?: "").observe(this) {
                    if(it.code == 0) {
                        user = null
                        token = null
                        getSharedPreferences("first", MODE_PRIVATE)
                            .edit().remove("token")
                            .apply()
                        startActivity(Intent(this, Class.forName("org.jash.sportsnews.NewsActivity")))
                    } else {
                        Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
}