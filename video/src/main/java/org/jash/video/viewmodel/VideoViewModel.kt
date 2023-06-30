package org.jash.video.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.jash.mylibrary.model.Res
import org.jash.mylibrary.model.VideoModel

class VideoViewModel:ViewModel() {
    var videos :LiveData<Res<List<VideoModel>>>? = null
}