package org.jash.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.token
import org.jash.mylibrary.network.user
import org.jash.profile.databinding.ActivitySystemSettingsBinding
import org.jash.profile.model.UpdatePasswordActivity

class SystemSettingsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySystemSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_system_settings)
        val menuId = intent.getIntExtra("menuId", 0)
        binding.navigation.inflateMenu(menuId)
        binding.navigation.setNavigationItemSelectedListener {
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
            when(it.itemId){
                R.id.profile -> {
                    startActivity(Intent(this, SystemSettingsActivity::class.java).apply {
                        putExtra("menuId", R.menu.settings_menu)
                    })
                    true
                }
                R.id.update_password -> {
                    startActivity(Intent(this, UpdatePasswordActivity::class.java))
                    true
                }
                R.id.logout -> {
                    service.logout().observe(this) {
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
                    true
                }
                R.id.edit -> {
                    startActivity(Intent(this, EditActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }
}