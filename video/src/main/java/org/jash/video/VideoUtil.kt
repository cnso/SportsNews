package org.jash.video

import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@BindingAdapter("android:video_url")
fun setVideoUrl(playerView: StyledPlayerView, url:String) {
    if (playerView.player == null) {
        playerView.player = ExoPlayer.Builder(playerView.context).build()
    } else {
        playerView.player?.clearMediaItems()
    }
    playerView.player?.also {
        it.addMediaItem(MediaItem.fromUri(url))
        it.prepare()
    }
}