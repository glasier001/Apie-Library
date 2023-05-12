package com.example.apielib.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.example.apielib.R
import com.example.apielib.databinding.FragmentNotesBinding
import com.example.apielib.utils.Station
import com.example.apielib.utils.saveBitmapToFile
import com.example.apielib.viewmodel.ApieVM
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment : Fragment() {

    private val apieVM: ApieVM by activityViewModels()
    private lateinit var binding: FragmentNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = apieVM
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apieVM.nextStation.observe(viewLifecycleOwner, Observer {
            if (it == Station.STATION_P) {
                layoutNotes.clearFocus()
                layoutNotes.saveBitmapToFile(context?.filesDir?.absolutePath + "/stationANotes.jpg")

            }
        })
    }

}
