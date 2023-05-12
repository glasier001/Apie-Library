package com.example.apielib.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.example.apielib.model.ApieSubmission
import com.example.apielib.utils.*
import com.example.apielib.viewmodel.ApieVM
import com.google.gson.Gson
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_apie.*

class ApieActivity : AppCompatActivity() {
    private val apieVM: ApieVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apie)
        floatingCountdownView.setOnCountdownEndListener { gotoNextStation() }

        apieVM.nextStation.observe(this, Observer { it ->
            floatingCountdownView.visibility = View.GONE
            when (it) {
                Station.STATION_A -> {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_nav_station_a)
                }
                Station.STATION_P -> {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_nav_station_p)
                }
                Station.STATION_I -> {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_nav_station_i)
                }
                Station.STATION_E -> {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_nav_station_e)
                }
                Station.STATION_SUBMIT -> {
                    floatingCountdownView.stop()
                }
            }


        })

        apieVM.apie.observe(this, Observer {
            it?.let {
                Log.e("apie", Gson().toJson(it))
                if(it.isReadyToSubmit){
                    setResult(Activity.RESULT_OK, Intent().putExtra("data", it))
                    finish()
                }
            }
        })

    }

    fun gotoNextStation(shouldShowCancelButton: Boolean = false) {
        val alertDialog = AlertDialog.Builder(this)
            .setMessage(if (shouldShowCancelButton) apieVM.nextStation.value?.message else apieVM.nextStation.value?.timeoutMessage)
            .setCancelable(false)
            .setPositiveButton(if (shouldShowCancelButton) "YES" else "OK") { _, _ ->
                apieVM.setNextStation()
                floatingCountdownView.stop()
            }
        if (shouldShowCancelButton) {
            alertDialog.setNegativeButton("NO") { _, _ -> }
        }
        alertDialog.create().show()
    }

    fun endApie() {
        AlertDialog.Builder(this)
            .setMessage(Station.STATION_E.message)
            .setPositiveButton("YES") { _, _ ->
                apieVM.endApie()
            }
            .setNegativeButton("NO") { _, _ -> }.create().show()

    }


}
