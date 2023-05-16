package com.example.apielib.dialog

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File


class DialogAudioFile {

    var isAudioFileShort =  true
    interface CallBack {
        fun onDoneClick();
        fun onCancelClick();
    }

    fun showDialog(context: Context, audioFileUrl: String, callBack: CallBack?) {
        try {
            isAudioFileShort = false
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_audio_file)

            val width = (context.getResources().getDisplayMetrics().widthPixels * 0.90) as Double
            val height = (context.getResources().getDisplayMetrics().heightPixels * 0.50) as Double

            dialog.getWindow()!!.setLayout(width.toInt(), height.toInt())

            val playerView = dialog.findViewById(R.id.playerView) as PlayerView
            val imgCancel = dialog.findViewById(R.id.imgCancel) as ImageView
            val btnSkip = dialog.findViewById(R.id.btnSkip) as Button
            val btnCancel = dialog.findViewById(R.id.btnCancel) as Button

            val player = ExoPlayerFactory.newSimpleInstance(
                context,
                DefaultTrackSelector(DefaultBandwidthMeter.Builder(context).build())
            )

            val downloadCache = SimpleCache(
                File(context.getCacheDir(), "exoCache"),
                NoOpCacheEvictor()
            )

            val uri = APIEPackage.apiePackageInfo.drugAudio

            val dataSourceFactory: DataSource.Factory =
                CacheDataSourceFactory(downloadCache, DefaultDataSourceFactory(context, "seyed"))

            val mediaSource: MediaSource =
                ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(
                    Uri.parse(uri)
                )

            player.addListener(object : Player.EventListener {
                override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {
                    Log.d("exo", "timeLine Changed")
                }

                override fun onTracksChanged(
                    trackGroups: TrackGroupArray,
                    trackSelections: TrackSelectionArray
                ) {
                }

                override fun onLoadingChanged(isLoading: Boolean) {
                    Log.d("exo", "loding changed= $isLoading")
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    Log.d("exo", "state changed")
                }

                override fun onRepeatModeChanged(repeatMode: Int) {}
                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
                override fun onPlayerError(error: ExoPlaybackException) {
                    Log.e("exo", "exoplayer error", error)
                }

                override fun onPositionDiscontinuity(reason: Int) {}
                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
                override fun onSeekProcessed() {
                    Log.d("exo", "seek processed")
                }
            })
            player.prepare(mediaSource)

            playerView.player = player

            player.playWhenReady = true

            btnSkip.setOnClickListener {
                callBack?.onDoneClick()
                player.stop()
                downloadCache.release()
                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                callBack?.onCancelClick()
                player.stop()
                downloadCache.release()
                dialog.dismiss()
            }

            imgCancel.setOnClickListener {
                callBack?.onDoneClick()
                player.stop()
                downloadCache.release()
                dialog.dismiss() }

            dialog.show()
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

}