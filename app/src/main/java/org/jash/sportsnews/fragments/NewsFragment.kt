package org.jash.sportsnews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.launcher.ARouter
import org.jash.mylibrary.logging.logging
import org.jash.sportsnews.R
import org.jash.sportsnews.adapter.PageAdapter
import org.jash.sportsnews.database.database
import org.jash.sportsnews.databinding.FragmentNewsBinding
import org.jash.sportsnews.network.service
import org.jash.sportsnews.viewmodel.MainViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var adapter:PageAdapter
    private val viewModel by activityViewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logging("onCreate")
        if (viewModel.categories == null) {
            viewModel.categories = service.getCategory()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logging("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        val binding:FragmentNewsBinding? = DataBindingUtil.bind(view)
        (activity as AppCompatActivity).setSupportActionBar(binding?.searchBar)
        adapter = PageAdapter(childFragmentManager)
        viewModel.categories?.observe(viewLifecycleOwner) {
            if (it.code == 0) {
                Thread {
                    it.data.forEach(context!!.database.getCategoryDao()::insert)
                    activity?.runOnUiThread {
                        adapter.data = it.data
                    }
                }.start()

            } else {
                logging(it)
                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
        binding?.let {
            it.page.adapter = adapter
            it.tab.setupWithViewPager(it.page)

            it.managerIcon.setOnClickListener {
                ARouter.getInstance()
                    .build("/news/manager")
                    .navigation()
            }
        }


    }
    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment()
    }
}