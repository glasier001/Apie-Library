package com.example.apielib.ui

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_admission.*
import kotlinx.android.synthetic.main.fragment_drug_video.*
import kotlinx.android.synthetic.main.fragment_drug_video.playerView
import kotlinx.android.synthetic.main.fragment_drug_video.progressBar
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo


class DrugVideoFragment : Fragment(R.layout.fragment_drug_video), Player.Listener {
    public var simpleExoPlayer: SimpleExoPlayer? = null
    private var secondVideoStarted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleExoPlayer = context?.let { SimpleExoPlayer.Builder(it).build() }!!
        initializeExoplayer(APIEPackage.apiePackageInfo.drugVideo)
    }

    private fun initializeExoplayer(url: String) {
        context?.let {
            VimeoExtractor.getInstance().fetchVideoWithIdentifier(
                url,
                APIEPackage.apiePackageInfo.hostUrl,
                object : OnVimeoExtractionListener {
                    override fun onSuccess(video: VimeoVideo?) {
                        activity?.runOnUiThread {
                            simpleExoPlayer = SimpleExoPlayer.Builder(it).build()
                            simpleExoPlayer?.prepare(
                                ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context!!))
                                    .createMediaSource(MediaItem.fromUri(Uri.parse(video?.streams?.get("720p"))))
                            )
                            playerView.player = simpleExoPlayer
                            simpleExoPlayer!!.addListener(this@DrugVideoFragment)
                            simpleExoPlayer!!.playWhenReady = true
                        }

                    }

                    override fun onFailure(throwable: Throwable?) {
                        activity?.runOnUiThread {
                            Toast.makeText(it, "Could not load video", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        when (playbackState) {
            Player.STATE_READY -> progressBar.visibility = View.GONE
            Player.STATE_BUFFERING -> progressBar.visibility = View.VISIBLE
            Player.STATE_ENDED -> {
//                if (!secondVideoStarted) playInstruction()
            }
        }
    }

    fun showInstruction() {
        txtInstruction.visibility = View.VISIBLE
    }

    fun playInstruction() {
        try {
            MediaPlayer().apply {
                setDataSource(APIEPackage.apiePackageInfo.drugAudio)
                prepare()
                start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadSecondVideo() {
        secondVideoStarted = true
        initializeExoplayer(APIEPackage.apiePackageInfo.drugVideo2)
    }

    override fun onDestroyView() {
        simpleExoPlayer?.release()
        super.onDestroyView()
    }

}