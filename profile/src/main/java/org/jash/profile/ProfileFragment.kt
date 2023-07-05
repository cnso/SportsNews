package org.jash.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableMap
import androidx.fragment.app.Fragment
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.network.service
import org.jash.mylibrary.network.user
import org.jash.profile.databinding.FragmentProfileBinding

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
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment()
    }
}