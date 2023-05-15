package com.example.apielib.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.ilhasoft.support.validation.Validator
import com.example.apielib.APIEPackage
import com.example.apielib.R
import com.example.apielib.databinding.FragmentNewsBinding
import com.example.apielib.utils.PdfRenderer
import com.example.apielib.utils.Station
import com.example.apielib.viewmodel.ApieVM
import kotlinx.android.synthetic.main.fragment_gcs.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.webView

class NewsFragment : Fragment() {

    private val apieVM: ApieVM by activityViewModels()
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = apieVM
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(APIEPackage.apiePackageInfo.chart_url)

        apieVM.nextStation.observe(viewLifecycleOwner, Observer {
            when (it) {
                Station.STATION_P, Station.STATION_SUBMIT -> (context?.filesDir?.absolutePath + "/stationAChart.pdf").let { file ->
                    PdfRenderer.printToPdf(webView, file, object : PdfRenderer.Callback {
                        override fun success(path: String?) {
                            apieVM.insertNewsChart(file)
                        }

                        override fun failure() {}

                    })
                }
                else ->{ Log.e("NewsFragment","else")}
            }
        })

    }
}



