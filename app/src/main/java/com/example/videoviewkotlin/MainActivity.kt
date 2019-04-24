package com.example.videoviewkotlin


import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var playbackPos = 0
    private val rtspUrl = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov"
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaController = MediaController(this)
        videoView_Livestream.setOnPreparedListener {
            mediaController.setAnchorView(videoContainer)
            videoView_Livestream.setMediaController(mediaController)
            videoView_Livestream.seekTo(playbackPos)
            videoView_Livestream.start()
        }

        videoView_Livestream.setOnInfoListener { player, what, extras ->
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                progressBar_Progress.visibility = View.INVISIBLE
            true
        }
    }

    override fun onStart() {
        super.onStart()

        val uri = Uri.parse(rtspUrl)
        videoView_Livestream.setVideoURI(uri)
        progressBar_Progress.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()

        videoView_Livestream.pause()
        playbackPos = videoView_Livestream.currentPosition
    }

    override fun onStop() {
        videoView_Livestream.stopPlayback()
        super.onStop()
    }
}
