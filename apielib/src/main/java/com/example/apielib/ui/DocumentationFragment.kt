package com.example.apielib.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.davemorrissey.labs.subscaleview.ImageSource

import com.example.apielib.R
import com.example.apielib.utils.Station
import com.example.apielib.viewmodel.ApieVM
import kotlinx.android.synthetic.main.activity_apie.*
import kotlinx.android.synthetic.main.fragment_documentation.*

class DocumentationFragment : Fragment(R.layout.fragment_documentation) {
    private val apieVM: ApieVM by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipPlan.visibility = when (apieVM.currentStation) {
            Station.STATION_P -> View.GONE
            else -> View.VISIBLE
        }

        chipDrugChart.visibility = when (apieVM.currentStation) {
            Station.STATION_E -> View.VISIBLE
            else -> View.GONE
        }
        chipNotes.setOnClickListener {
            findNavController().navigate(
                PhotoDialogDirections.actionGlobalPhotoDialog(
                    context?.filesDir?.absolutePath + "/stationANotes.jpg"
                )
            )
        }
        chipPlan.setOnClickListener {
            findNavController().navigate(
                PhotoDialogDirections.actionGlobalPhotoDialog(
                    context?.filesDir?.absolutePath + "/stationPChart.jpg"
                )
            )
        }
        chipDrugChart.setOnClickListener {
            findNavController().navigate(
                PdfViewDialogDirections.actionGlobalPdfviewDialog(context?.filesDir?.absolutePath + "/stationIChart.pdf")
            )
        }


    }


}
