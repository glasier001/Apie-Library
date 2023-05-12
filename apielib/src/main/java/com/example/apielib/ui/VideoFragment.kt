package com.example.apielib.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.apielib.APIEPackage
import com.example.apielib.databinding.FragmentVideoBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_drug_video.*
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo

class VideoFragment : DialogFragment() {
    public var simpleExoPlayer: SimpleExoPlayer? = null
    private val args: VideoFragmentArgs by navArgs()
    private lateinit var binding: FragmentVideoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            it.requestFeature(Window.FEATURE_NO_TITLE)

        }
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        initializeExoplayer()
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }


    override fun onDestroyView() {
        simpleExoPlayer?.release()
        super.onDestroyView()
    }
    private fun initializeExoplayer() {
        context?.let {
            VimeoExtractor.getInstance().fetchVideoWithURL(
                args.url,
                null,
                object : OnVimeoExtractionListener {
                    override fun onSuccess(video: VimeoVideo?) {
                        activity?.runOnUiThread {
                            simpleExoPlayer = SimpleExoPlayer.Builder(it).build()
                            simpleExoPlayer!!.prepare(
                                ProgressiveMediaSource.Factory(
                                    DefaultHttpDataSourceFactory(
                                        Util.getUserAgent(
                                            it,
                                            it.packageName
                                        )
                                    )
                                )
                                    .createMediaSource(Uri.parse(video?.streams?.get("720p")))
                            )
                            binding.videoView.player = simpleExoPlayer
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
}