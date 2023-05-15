package com.example.apielib.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.example.apielib.databinding.FragmentScenarioBinding
import com.example.apielib.utils.ApieService
import com.example.apielib.utils.Station
import com.example.apielib.utils.Status
import kotlinx.android.synthetic.main.activity_apie.*
import kotlinx.android.synthetic.main.fragment_scenario.*
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class ScenarioFragment : Fragment() {

    private val args: ScenarioFragmentArgs by navArgs()
    private lateinit var binding: FragmentScenarioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_scenario, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ApieActivity).txtTitle.text = args.station.scenarioText

        webView.loadData(args.station.scenario, null, null)
        btnOk.setOnClickListener {
            when (args.station) {
                Station.STATION_A ->
                    findNavController().navigate(R.id.action_scenarioFragment_to_stationAFragment)
                Station.STATION_P ->
                    findNavController().navigate(R.id.action_scenarioFragment_to_stationPFragment)
                Station.STATION_I ->
                    findNavController().navigate(R.id.action_scenarioFragment_to_stationIFragment)
                Station.STATION_E ->
                    findNavController().navigate(R.id.action_scenarioFragment_to_stationEFragment)
                else ->{ Log.e("ScenarioFragmentA","else")}
            }
            when (args.station) {
                Station.STATION_P, Station.STATION_I, Station.STATION_E -> {
                    (activity as ApieActivity).floatingCountdownView.visibility = View.VISIBLE
                    (activity as ApieActivity).floatingCountdownView.start(APIEPackage.STATION_DURATION)
                }
                else ->{ Log.e("ScenarioFragmentB","else")}
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(requireActivity())
                .setMessage("Do you want to exit this APIE package?").setCancelable(false)
                .setPositiveButton("YES") { _, _ -> requireActivity().finish() }
                .setNegativeButton("NO") { _, _ -> }
                .create().show()
        }
    }
}