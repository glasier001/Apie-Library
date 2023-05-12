package com.example.apieproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.invoke
import com.example.apielib.FetchApieSubmission
import com.example.apielib.model.ApiePackageInfo
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val getApieSubmission =
        registerForActivityResult(FetchApieSubmission()) { apieSubmission ->
            val json = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                .toJson(apieSubmission)
            Log.e("apie submission", json)
//        Log.e("apie info", Gson().toJson(APIEPackage.apiePackageInfo))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGoNews.setOnClickListener { executeFlow("news") }
        btnGoGcs.setOnClickListener { executeFlow("gcs") }


    }

    private fun executeFlow(chartType: String) {
        getApieSubmission(
            ApiePackageInfo(
                "12",
                "15",
                "",
                "APIE Package 1",
                "120",
                "1",
                "20",
                "",
                "",
                "https://www.osceapp.com/chart",
                "https://www.osceapp.com/gcs_chart",
                "Scenario A Text",
                "Scenario P Text",
                "Scenario I Text",
                "Scenario E Text",
                "http://glasier.in/npdf/01_Admissionsheet_MiaKhatar.pdf",
                "Nursing Assessment Candidate Notes \n" +
                        "Mia Khatar, 0145692498 \n41 Almond Close, Tatterell, LL12 TBU \n" +
                        "25/02/1975",
                "441703138",
                chartType,
                "Mia Khatar, Hospital Number 0145692498\n" +
                        "41 Almond Close, Tatterell, LL12 TBU\nDOB 25/02/1975",
                "https://www.osceapp.com/api/20200730061028d20200714102809dInstructions.mp3",
                "441703262",
//                "https://player.vimeo.com/video/353254220",
                "https://player.vimeo.com/video/441703094",
                "https://www.osceapp.com/drug_chart",
                "https://www.osceapp.com"
            )
        )
    }
}
