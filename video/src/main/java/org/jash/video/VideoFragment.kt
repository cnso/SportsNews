package org.jash.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class VideoFragment : Fragment(R.layout.fragment_video) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            VideoFragment()
    }
}