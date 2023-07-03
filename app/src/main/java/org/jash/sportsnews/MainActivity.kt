package org.jash.sportsnews

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
//import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.network.token

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = getSharedPreferences("first", MODE_PRIVATE)
        token = preferences.getString("token", null)
        if (preferences.getBoolean("isFirst", true)) {
            val string = resources.getString(R.string.user_context)
            val context = SpannableString(string)
            context.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    logging("SpannableString")
                    Toast.makeText(this@MainActivity, "点击文字", Toast.LENGTH_SHORT).show()
                }

//                override fun updateDrawState(ds: TextPaint) {
//                    ds.setColor(Color.parseColor("#0000ff"))
//                }
            }, 15, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            context.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    logging("SpannableString")
                    Toast.makeText(this@MainActivity, "点击文字", Toast.LENGTH_SHORT).show()
                }

//                override fun updateDrawState(ds: TextPaint) {
//                    ds.setColor(Color.parseColor("#0000ff"))
//                }
            }, 22, 28, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.user_title)
                .setMessage(context)
                .setPositiveButton(R.string.agree) { _, _ ->
                    startActivity(Intent(this, NewsActivity::class.java))
//                    ARouter.getInstance().build("/news/main").navigation()
                    preferences.edit().putBoolean("isFirst", false).apply()
                    finish()
                }
                .setNegativeButton(R.string.leave) { _, _ ->
                    finishAffinity()
                }
                .create()
            dialog.show()
            // 富文本点击事件生效,android 8 以后要写的
            val message = dialog.window?.findViewById<TextView>(android.R.id.message)
            message?.also {
                it.movementMethod = LinkMovementMethod.getInstance()
                it.highlightColor = Color.TRANSPARENT
            }
            dialog.getButton(DialogInterface.BUTTON_POSITIVE)?.setTextColor(Color.parseColor("#0000ff"))
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE)?.setTextColor(Color.parseColor("#0000ff"))

        } else {
            startActivity(Intent(this, NewsActivity::class.java))
//            ARouter.getInstance().build("/news/main").navigation()
            finish()
        }
    }
}