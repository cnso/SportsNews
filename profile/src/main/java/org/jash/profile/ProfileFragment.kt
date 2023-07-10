package org.jash.profile

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableMap
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.uuzuche.lib_zxing.activity.CaptureActivity
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.user
import org.jash.profile.databinding.FragmentProfileBinding
import com.uuzuche.lib_zxing.activity.CodeUtils

import android.R.attr.data
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PermissionResult

import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE




class ProfileFragment : Fragment(R.layout.fragment_profile) {
    var binding:FragmentProfileBinding? = null
    val map: ObservableMap<String, String> = ObservableArrayMap<String, String>().apply {
        this["follows"] = ""
        this["fans"] = ""
        this["comments"] = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        binding?.user = user
        binding?.map = map
        service.getFans().observe(viewLifecycleOwner) {
            if (it.code == 0) {
                map["follows"] = it.data["follows"]
                map["fans"] = it.data["fans"]
            } else {
                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
        service.getCommentAll().observe(viewLifecycleOwner) {
            if (it.code == 0) {
                map["comments"] = it.data.filter { comment -> comment.uid == user?.id }.size.toString()
            } else {
                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
        binding?.follows?.setOnClickListener {
            startActivity(Intent(context, UserListActivity::class.java).apply {
                putExtra("isFollows", true)
            })
        }
        binding?.fans?.setOnClickListener {
            startActivity(Intent(context, UserListActivity::class.java).apply {
                putExtra("isFollows", false)
            })
        }
        binding?.settings?.setOnClickListener {
            startActivity(Intent(context, SystemSettingsActivity::class.java).apply {
                putExtra("menuId", R.menu.system_menu)
            })
        }
        binding?.scanner?.setOnClickListener {
            if(activity?.checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED) {

                result.launch(Intent(context, CaptureActivity::class.java))

//                startActivityForResult(Intent(context, CaptureActivity::class.java), 1)
            } else {
                permissionResult.launch("android.permission.CAMERA")

            }
        }
    }
    private val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        //处理扫描结果（在界面上显示）
        if (null != it.data){
            val bundle: Bundle? = it.data?.extras

            if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS){
                val result: String? = bundle.getString(CodeUtils.RESULT_STRING)
                Toast.makeText(context, "新的 ForResult:" + result, Toast.LENGTH_LONG).show()
            } else if (bundle?.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED){
                Toast.makeText(context , "解析二维码失败", Toast.LENGTH_LONG).show()
            }
        }
    }
    private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it) {
            result.launch(Intent(context, CaptureActivity::class.java))
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment()
    }
}