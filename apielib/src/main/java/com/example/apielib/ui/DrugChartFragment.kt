package com.example.apielib.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.example.apielib.utils.PdfRenderer
import com.example.apielib.utils.Station
import com.example.apielib.viewmodel.ApieVM
import kotlinx.android.synthetic.main.fragment_drug_chart.*
import kotlinx.android.synthetic.main.fragment_drug_chart.webView
import kotlinx.android.synthetic.main.fragment_gcs.*

class DrugChartFragment : Fragment(R.layout.fragment_drug_chart) {

    private val apieVM: ApieVM by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.loadUrl(APIEPackage.apiePackageInfo.drug_chart_url + "/" + APIEPackage.apiePackageInfo.id)
        apieVM.nextStation.observe(viewLifecycleOwner, Observer {
            when (it) {
                Station.STATION_E, Station.STATION_SUBMIT -> (context?.filesDir?.absolutePath + "/stationIChart.pdf").let { file ->
                    PdfRenderer.printToPdf(webView, file, object : PdfRenderer.Callback {
                        override fun success(path: String?) {
                            apieVM.insertDrugChart(file)
                        }

                        override fun failure() {}

                    })
                }
            }
        })

    }

}
