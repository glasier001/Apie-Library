package com.example.apielib.ui

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.apielib.APIEPackage

import com.example.apielib.R
import com.example.apielib.databinding.FragmentAdmissionBinding
import com.example.apielib.utils.Status
import com.example.apielib.viewmodel.AdmissionVM
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.video.VideoRendererEventListener
import com.google.android.exoplayer2.video.VideoSize
import kotlinx.android.synthetic.main.activity_apie.*
import kotlinx.android.synthetic.main.fragment_admission.*
import kotlinx.android.synthetic.main.fragment_admission.pdfView
import kotlinx.android.synthetic.main.fragment_admission.progressBar
import kotlinx.android.synthetic.main.fragment_scenario.*
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo


class AdmissionFragment : Fragment(), Player.Listener, VideoRendererEventListener {
    val admissionVM: AdmissionVM by activityViewModels()
    private lateinit var binding: FragmentAdmissionBinding
    var simpleExoPlayer: SimpleExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_admission, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = admissionVM
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countdownVideo.setOnCountdownEndListener {
            startPatientVideo()
            (activity as ApieActivity).floatingCountdownView.visibility = View.VISIBLE
            (activity as ApieActivity).floatingCountdownView.start(APIEPackage.STATION_DURATION)
        }
        countdownVideo.start(APIEPackage.PATIENT_VIDEO_DURATION)
        initializeExoplayer()

        (activity as ApieActivity).imgPlayVideo.setOnClickListener { if (it.isVisible) toggleVideoFragmentVisibility() }
        admissionVM.file.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                if (resource.status == Status.SUCCESS)
                    resource.data?.let { stream ->
                        pdfView.fromStream(stream).onLoad { progressBarPdf.visibility = View.GONE }
                            .load()
                    }
            }
        })
    }

    private fun startPatientVideo() {
        layoutPlayer.visibility = View.VISIBLE
        (activity as ApieActivity).imgPlayVideo.visibility = View.VISIBLE
        simpleExoPlayer?.playWhenReady = true
        txtVideo.text = "Video is playing"
        countdownVideo.visibility = View.GONE
    }


    private fun initializeExoplayer() {
        context?.let {
            VimeoExtractor.getInstance().fetchVideoWithIdentifier(
                APIEPackage.apiePackageInfo.patientVideo,
                APIEPackage.apiePackageInfo.hostUrl,
                object : OnVimeoExtractionListener {
                    override fun onSuccess(video: VimeoVideo?) {
                        activity?.runOnUiThread {
                            simpleExoPlayer = SimpleExoPlayer.Builder(it).build()
                            simpleExoPlayer?.prepare(
                                ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context!!))
                                    .createMediaSource(MediaItem.fromUri(Uri.parse(video?.streams?.get("720p"))))
                            )
                            playerView?.player = simpleExoPlayer
                            simpleExoPlayer?.addListener(this@AdmissionFragment)
//                            simpleExoPlayer?.addVideoListener(this@AdmissionFragment)
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

    private fun toggleVideoFragmentVisibility() {
        if (layoutPlayer.isVisible)
            layoutPlayer.visibility = View.GONE
        else layoutPlayer.visibility =
            View.VISIBLE
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        when (playbackState) {
            Player.STATE_READY -> {
                progressBar.visibility = View.GONE
            }
            Player.STATE_BUFFERING -> progressBar.visibility = View.VISIBLE
            Player.STATE_ENDED -> {
                progressBar.visibility = View.GONE
                txtVideo.text = "Video has finished"
                countdownVideo.visibility = View.GONE
            }
        }
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        Log.e("videosize",videoSize.height.toString())

        var p: ViewGroup.LayoutParams = playerView.layoutParams
        var currWidth: Int = playerView.width

        p.width = currWidth
        p.height = (videoSize.height /videoSize.width * currWidth).toFloat().toInt()
        playerView.requestLayout()
    }

//    override fun onVideoSizeChanged(
//        width: Int,
//        height: Int,
//        unappliedRotationDegrees: Int,
//        pixelWidthHeightRatio: Float
//    ) {
//        super.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio)
//        var p: ViewGroup.LayoutParams = playerView.layoutParams
//        var currWidth: Int = playerView.width
//
//        p.width = currWidth
//        p.height = (height / width * currWidth).toFloat().toInt()
//        playerView.requestLayout()
//    }

    override fun onDestroyView() {
        simpleExoPlayer?.release()
        super.onDestroyView()
    }
}
