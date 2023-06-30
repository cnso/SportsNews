package org.jash.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.jash.mylibrary.adapter.CommonAdapter
import org.jash.mylibrary.model.VideoModel
import org.jash.mylibrary.network.service
import org.jash.video.databinding.FragmentVideoBinding
import org.jash.video.viewmodel.VideoViewModel

class VideoFragment : Fragment(R.layout.fragment_video) {
    val viewModel by activityViewModels<VideoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.videos == null) {
            viewModel.videos = service.getVideo()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = DataBindingUtil.bind<FragmentVideoBinding>(view)
        val adapter = CommonAdapter(mapOf(VideoModel::class.java to (R.layout.video_item to BR.video)))
        bind?.adapter = adapter
        viewModel.videos?.observe(viewLifecycleOwner) {
            adapter += it.data.filter { !it.videourl.isNullOrEmpty() }
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            VideoFragment()
    }
}