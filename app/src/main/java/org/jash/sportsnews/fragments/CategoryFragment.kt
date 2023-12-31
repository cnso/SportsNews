package org.jash.sportsnews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import org.jash.mylibrary.adapter.CommonAdapter
import org.jash.sportsnews.R
import org.jash.mylibrary.database.database
import org.jash.sportsnews.databinding.FragmentCategoryBinding
import org.jash.mylibrary.model.Category
import org.jash.mylibrary.model.Record
import org.jash.mylibrary.network.service
import org.jash.sportsnews.BR
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment(R.layout.fragment_category) {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var category: LiveData<Category>? = null
    private val adapter =
        CommonAdapter(mapOf(Record::class.java to (R.layout.record_item to BR.record)))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        context?.let {
            category = it.database.getCategoryDao().find(param1 ?: 0)
        }
        category?.observe(this) {
            service.getRecord(it.id, 1, 10).observe(this) { res ->
                if (res.code == 0) {
                    adapter += res.data.records
                    thread { context?.let { it1 -> res.data.records.forEach(it1.database.getRecordDao()::insert) } }

                } else {
                    Toast.makeText(context, res.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = DataBindingUtil.bind<FragmentCategoryBinding>(view)
        bind?.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}