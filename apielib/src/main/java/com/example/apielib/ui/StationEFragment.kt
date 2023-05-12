package com.example.apielib.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.example.apielib.utils.Station
import com.example.apielib.viewmodel.ApieVM
import com.google.android.material.tabs.TabLayoutMediator
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.VideoResult
import kotlinx.android.synthetic.main.activity_apie.*
import kotlinx.android.synthetic.main.fragment_station_e.*
import java.io.File


class StationEFragment : Fragment(R.layout.fragment_station_e) {
    private val apieVM: ApieVM by activityViewModels()
    private var state: Boolean? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ApieActivity).txtTitle.text = "Station E"
        viewPagerE.isUserInputEnabled = false
        viewPagerE.adapter = EViewPagerAdapter(this)
        TabLayoutMediator(tabLayoutE, viewPagerE) { tab, position ->
            tab.text = when (position) {
                0 -> "Camera"
                else -> "Documentation"
            }
            viewPagerE.setCurrentItem(0, true)


        }.attach()
        runWithPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) {
            cameraView.setLifecycleOwner(this)
            cameraView.addCameraListener(listener)
        }
        btnVideoRecord.setOnClickListener {
            runWithPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) {
                state?.let { if (it) stopRecording() } ?: startRecording()

            }

        }
        videoCountdownView.setOnCountdownEndListener { stopRecording() }

        btnESubmit.setOnClickListener {
            with(activity as ApieActivity) {
                gotoNextStation(true)
            }
        }

        apieVM.nextStation.observe(viewLifecycleOwner, Observer {
            if (it == Station.STATION_SUBMIT)
                if (cameraView.isTakingVideo) stopRecording() else apieVM.insertVideoRecording()
        })

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(requireActivity())
                .setMessage("Do you want to exit this APIE package?").setCancelable(false)
                .setPositiveButton("YES") { _, _ -> requireActivity().finish() }
                .setNegativeButton("NO") { _, _ -> }
                .create().show()
        }
    }

    private val listener = object : CameraListener() {
        override fun onVideoRecordingStart() {
            super.onVideoRecordingStart()
            videoCountdownView.start(APIEPackage.STATION_E_VIDEO_DURATION)
        }

        override fun onVideoRecordingEnd() {
            super.onVideoRecordingEnd()
            videoCountdownView.stop()
        }

        override fun onVideoTaken(result: VideoResult) {
            super.onVideoTaken(result)
            Log.e("file size: ", "" + (result.file.length()/(1024.0 * 1024)))
            apieVM.insertVideoRecording()
        }
    }

    private fun startRecording() {
        state = true
        apieVM.videoRecording =
            context?.filesDir?.absolutePath + "/video.mp4"
        btnVideoRecord.displayVideoRecordStateInProgress()
        cameraView.takeVideo(File(apieVM.videoRecording))
    }

    private fun stopRecording() {
        btnVideoRecord.displayVideoRecordStateReady()
        cameraView.stopVideo()
        state = false
    }

    inner class EViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = 2

        override fun createFragment(position: Int) = when (position) {
            0 -> CameraFragment()
            else -> DocumentationFragment()
        }


    }
}

class CameraFragment : Fragment(R.layout.fragment_camera)
