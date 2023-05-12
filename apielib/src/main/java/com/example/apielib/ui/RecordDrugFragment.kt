package com.example.apielib.ui

import android.Manifest
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.example.apielib.utils.Station
import com.example.apielib.viewmodel.ApieVM
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.fragment_record_drug.*
import java.io.IOException


class RecordDrugFragment : Fragment(R.layout.fragment_record_drug) {
    private val apieVM: ApieVM by activityViewModels()
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean? = null
    private var videoShown:Boolean = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaRecorder = MediaRecorder()
        btnRecord.setOnClickListener {
            runWithPermissions(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) {
                if (state != true)
                    startRecording()
                else
                    stopRecording()

            }
        }

        apieVM.nextStation.observe(viewLifecycleOwner, Observer {
            when (it) {
                Station.STATION_E, Station.STATION_SUBMIT -> {
                    state?.let { isRecording -> if (isRecording) stopRecording() }
                }
            }
        })
    }

    private fun startRecording() {
        try {
            apieVM.drugRecording =
                context?.filesDir?.absolutePath + "/audio.mp3"
            mediaRecorder?.let {
                it.setAudioSource(MediaRecorder.AudioSource.MIC)
                it.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                it.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                it.setOutputFile(apieVM.drugRecording)
                it.prepare()
                it.start()
            }
            state = true
            btnRecord.setImageResource(R.drawable.ic_stop)
            txtRecordStatus.text = "Recording..."
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.let {
            it.stop()
//            it.release()
            chronometer.stop()
            state = false
            btnRecord.setImageResource(R.drawable.ic_mic)
            txtRecordStatus.text = "Recording finished. Click to record again"
        }
        if(!videoShown){
            videoShown = true
            findNavController().navigate(VideoFragmentDirections.actionGlobalNavVideo(APIEPackage.apiePackageInfo.drugVideo2))
        }
    }

    override fun onDestroyView() {
        mediaRecorder?.release()
        super.onDestroyView()
    }

}