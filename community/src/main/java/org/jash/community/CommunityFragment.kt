package org.jash.community

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.jash.community.databinding.FragmentCommunityBinding
import org.jash.mylibrary.adapter.CommonAdapter
import org.jash.mylibrary.model.Record
import org.jash.mylibrary.network.service


class CommunityFragment : Fragment(R.layout.fragment_community) {
    lateinit var adapter: CommonAdapter<Record>
    var binding: FragmentCommunityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CommonAdapter(mapOf(Record::class.java to (R.layout.record_item2 to BR.record)))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        binding?.adapter = adapter
       ( activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        activity?.title = "我的收藏"
        service.getNewsCollect().observe(viewLifecycleOwner) {
            if (it.code == 0) {
                adapter += it.data
            } else {
                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CommunityFragment()
    }
}