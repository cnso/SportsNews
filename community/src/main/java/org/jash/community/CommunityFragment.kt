package org.jash.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class CommunityFragment : Fragment(R.layout.fragment_community) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    companion object {

        @JvmStatic
        fun newInstance() =
            CommunityFragment()
    }
}