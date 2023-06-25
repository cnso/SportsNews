package org.jash.sportsnews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.jash.mylibrary.adapter.CommonAdapter
import org.jash.sportsnews.BR
import org.jash.sportsnews.R
import org.jash.sportsnews.database.database
import org.jash.sportsnews.databinding.FragmentTopBinding
import org.jash.sportsnews.model.Record
import org.jash.sportsnews.network.service
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [TopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopFragment : Fragment(R.layout.fragment_top) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private val adapter =
        CommonAdapter(mapOf(Record::class.java to (R.layout.record_item to BR.record)))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        service.getTop(param1 ?: "").observe(this) {
            if (it.code == 0) {
                adapter += it.data
                thread { context?.let { it1 -> it.data.forEach(it1.database.getRecordDao()::insert) } }

            } else {
                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = DataBindingUtil.bind<FragmentTopBinding>(view)
        bind?.adapter = adapter
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            TopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}