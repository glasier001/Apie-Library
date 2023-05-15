package com.example.apielib.ui

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.apielib.APIEPackage

import com.example.apielib.R
import com.example.apielib.databinding.FragmentPlanBinding
import com.example.apielib.utils.Station
import com.example.apielib.utils.saveBitmapToFile
import com.example.apielib.viewmodel.ApieVM
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_apie.*
import kotlinx.android.synthetic.main.fragment_plan.*

class PlanFragment : Fragment(), SignDialog.OnSignedListener {

    private val apieVM: ApieVM by activityViewModels()
    private lateinit var binding: FragmentPlanBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as ApieActivity).imgPlayVideo.visibility = View.GONE
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_plan, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = apieVM
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgSign.setOnClickListener {
            activity?.supportFragmentManager?.let { fm ->
                SignDialog().apply {
                    onSignedListener = this@PlanFragment
                }.show(fm, "dialog")
            }
        }
        apieVM.nextStation.observe(viewLifecycleOwner, Observer {
            when (it) {
                Station.STATION_I, Station.STATION_SUBMIT -> {
                    layoutPlan.clearFocus()
                    with(context?.filesDir?.absolutePath + "/stationPChart.jpg"){
                        layoutPlan.saveBitmapToFile(this)
                        apieVM.insertPlanChart(this)
                    }
                }
                else ->{ Log.e("PlanFragment","else")}
            }
        })

    }

    override fun onSigned(bmp: Bitmap) {
        imgSign.setImageBitmap(bmp)
    }

}
