package org.jash.community

import android.os.Bundle
import androidx.fragment.app.Fragment


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