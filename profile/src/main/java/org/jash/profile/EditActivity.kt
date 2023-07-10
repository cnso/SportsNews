package org.jash.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.core.graphics.set
import androidx.databinding.DataBindingUtil
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import com.google.android.material.dialog.MaterialDialogs
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.user
import org.jash.profile.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditActivity : AppCompatActivity() {
    val binding by lazy { DataBindingUtil.setContentView<ActivityEditBinding>(this, R.layout.activity_edit) }
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            listOf("男", "女", "未知")
        ) {
            override fun getFilter(): Filter {
                return object :Filter() {
                    override fun performFiltering(constraint: CharSequence?): FilterResults? {
                        return null
                    }

                    override fun publishResults(
                        constraint: CharSequence?,
                        results: FilterResults?
                    ) {
                    }

                }
            }
        }
        binding.spinner.setAdapter(adapter)
        binding.user = user
        binding.birthday.setOnClickListener {
            val c:Calendar = Calendar.getInstance()
            if (!user?.birthday.isNullOrEmpty()) {
                try{
                    c.time = sdf.parse(user?.birthday)
                } catch (_:Exception) {

                }

            }
            MaterialStyledDatePickerDialog(this, { _, y, m, d ->
                binding.birthday.text = String.format("%04d-%02d-%02d", y, m + 1, d)
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) )
                .show()
        }
        binding.showme.setOnClickListener {
            val writer:MultiFormatWriter = MultiFormatWriter()
            var matrix = writer.encode(
                "用户: ${user?.username}, 性别: ${user?.gender}, 生日: ${user?.birthday}",
                BarcodeFormat.QR_CODE,
                500,
                500,
                mapOf(EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H, EncodeHintType.CHARACTER_SET to "UTF-8")
            )
            val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565)
            for (i  in 0 until matrix.width) {
                for(j in 0 until matrix.height) {
                    bitmap.set(i, j, if (matrix.get(i, j)) Color.parseColor("#000000") else Color.parseColor("#ffffff"))
                }
            }
            AppCompatDialog(this).apply {
                setOnDismissListener {  }
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                addContentView(ImageView(this@EditActivity).apply {
                    setImageBitmap(bitmap)
                }, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            }.show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.finish -> {
                service.updateProfile(user).observe(this) {
                    if (it.code == 0) {
                        Toast.makeText(this, "资料修改成功", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}